package com.spring.simple.development.core.component.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.IpUtil;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import com.spring.simple.development.support.utils.log.ErrorMessage;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * 在dubbo原有的ExceptionFilter上做修改，当dubbo抛出抛出ManilaException时，直接抛给消费者，不做包装
 * ExceptionInvokerFilter
 * <p>
 * 功能：
 * <ol>
 * <li>不期望的异常打ERROR日志（Provider端）<br>
 *     不期望的日志即是，没有的接口上声明的Unchecked异常。
 * <li>异常不在API包中，则Wrap一层RuntimeException。<br>
 *     RPC对于第一层异常会直接序列化传输(Cause异常会String化)，避免异常在Client出不能反序列化问题。
 * </ol>
 *
 * @author
 */
@Activate(group = Constants.PROVIDER, value = "springDubboExceptionFilter")
public class SpringSimpleDubboExceptionFilter implements Filter {
    private static final org.apache.logging.log4j.Logger errorLogMessageLogger = LogManager.getLogger("errorLogMessage");


    private final Logger logger;

    public SpringSimpleDubboExceptionFilter() {
        this(LoggerFactory.getLogger(SpringSimpleDubboExceptionFilter.class));
    }

    public SpringSimpleDubboExceptionFilter(Logger logger) {
        this.logger = logger;
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();
                    // 告警接入
                    if (exception instanceof GlobalException == false) {
                        collectionLog(exception, invocation);
                    }
                    // 如果是checked异常，直接抛出
                    if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                        return result;
                    }
                    // 在方法签名上有声明，直接抛出
                    try {
                        Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                        Class<?>[] exceptionClassses = method.getExceptionTypes();
                        for (Class<?> exceptionClass : exceptionClassses) {
                            if (exception.getClass().equals(exceptionClass)) {
                                return result;
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        return result;
                    }

                    // ManilaException，直接抛出
                    if (exception instanceof GlobalException) {
                        // 打印业务异常warn日志
                        logger.warn("Business exception which called by " + RpcContext.getContext().getRemoteHost()
                                + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                                + ", exception: " + exception.getClass().getName() + ": " + ((GlobalException) exception).getContent(), exception);
                        return new RpcResult(new GlobalException(new GlobalResponseCode(((GlobalException) exception).getErrorCode(), ((GlobalException) exception).getContent())));
                    }

                    // 未在方法签名上定义的异常，在服务器端打印ERROR日志
                    logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                            + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                    // 异常类和接口类在同一jar包里，直接抛出
                    String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                    String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                    if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                        return result;
                    }
                    // 是JDK自带的异常，直接抛出
                    String className = exception.getClass().getName();
                    if (className.startsWith("java.") || className.startsWith("javax.")) {
                        return result;
                    }
                    // 是Dubbo本身的异常，直接抛出
                    if (exception instanceof RpcException) {
                        return result;
                    }

                    // 否则，包装成RuntimeException抛给客户端
                    return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
                } catch (Throwable e) {
                    logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                            + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                    return result;
                }
            }
            return result;
        } catch (RuntimeException e) {
            logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }

    private void collectionLog(Throwable e, Invocation invocation) {
        try {
            boolean isOpen = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_IS_OPEN));
            if (isOpen) {
                // 日志转转
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                String errorMsg = stringWriter.toString();
                // 记录日志内容
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setLogId(PrimaryKeyGenerator.getInstance().nextId().toString());
                errorMessage.setProjectType("api");
                errorMessage.setLogType("restful api");
                errorMessage.setIp(IpUtil.getIp());
                errorMessage.setLogPath("/data/logs/simple-development-core/error/error.log");
                errorMessage.setDate(DateUtils.getCurrentTime());
                errorMessage.setContent(errorMsg);
                errorMessage.setDescription(e.getMessage());
                errorMessage.setUrl(invocation.getInvoker().getUrl().getAddress());
                errorMessage.setRemoteIp(invocation.getInvoker().getUrl().toString());
                errorLogMessageLogger.info(JSON.toJSONString(errorMessage));
                // 添加报警信息
                Class<?> simpleAlertExecutor = Class.forName("com.spring.simple.development.core.component.alertsdk.SimpleAlertExecutor");
                Method method = simpleAlertExecutor.getDeclaredMethod("sendMediumMessage", String.class);
                method.invoke(simpleAlertExecutor.newInstance(), errorMessage.toString());
            }

        } catch (Exception ex) {
            logger.error("收集错误日志错误:", e);
        }
    }

}