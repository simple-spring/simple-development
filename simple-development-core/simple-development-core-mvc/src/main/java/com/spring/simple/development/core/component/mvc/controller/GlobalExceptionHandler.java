package com.spring.simple.development.core.component.mvc.controller;


import com.acl.xauth.exception.authc.NoLoginException;
import com.alibaba.fastjson.JSON;
import com.spring.simple.development.core.component.alertsdk.SimpleAlertExecutor;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.exception.NoPermissionException;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.IpUtil;
import com.spring.simple.development.support.utils.NewIpUtil;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import com.spring.simple.development.support.utils.log.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局异常处理，由spring mvc拦截异常，做统一的处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final Logger errorLogMessageLogger = LogManager.getLogger("errorLogMessage");

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResBody processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        logger.error(DateUtils.getCurrentTime() + e.getMessage(), e);
        // 业务异常处理
        if (e instanceof GlobalException) {
            return ResBody.buildFailResBody(new GlobalResponseCode(((GlobalException) e).getErrorStatus(), ((GlobalException) e).getErrorCode(), ((GlobalException) e).getContent()));
        }
        // 无权限处理,重定向至无权限页面,
        if (e instanceof NoPermissionException || e instanceof com.acl.xauth.exception.authz.NoPermissionException) {
            //告诉ajax我是重定向
            response.setHeader("REDIRECT", "PERMISSION");
            //告诉ajax我重定向的路径
            response.setHeader("URL", request.getContextPath() + "/noPermission.html");
            return ResBody.buildFailResBody(GlobalResponseCode.NO_PERMISSION);
        }
        if (e instanceof NoLoginException) {
            //告诉ajax我是重定向
            response.setHeader("REDIRECT", "NoLogin");
            //告诉ajax我重定向的路径
            response.setHeader("URL", request.getContextPath() + "/login.html");
            return ResBody.buildFailResBody(GlobalResponseCode.SYS_NO_LOGIN);
        }

        // 收集错误日志
        collectionLog(request, e);
        return ResBody.buildFailResBody();
    }

    private void collectionLog(HttpServletRequest request, Throwable e) {
        try {
            // 日志转转
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            String errorMsg = stringWriter.toString();
            // 记录日志内容
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setLogId(PrimaryKeyGenerator.getInstance().nextId().toString());
            errorMessage.setProjectType("api");
            errorMessage.setLogType("restful api");
            errorMessage.setIp(NewIpUtil.getIp());
            errorMessage.setLogPath("/data/logs/simple-development-core/error/error.log");
            errorMessage.setDate(DateUtils.getCurrentTime());
            errorMessage.setContent(errorMsg);
            errorMessage.setDescription(e.getMessage());
            errorMessage.setUrl(request.getRequestURI());
            errorMessage.setRemoteIp(getRemoteIpAddr(request));
            errorLogMessageLogger.info(JSON.toJSONString(errorMessage));
            boolean isOpen = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_IS_OPEN));
            if (isOpen) {
                // 添加报警信息
                SimpleAlertExecutor.sendHighMessage(errorMessage.toString());
            }

        } catch (Exception ex) {
            logger.error("收集错误日志错误:", e);
        }
    }
    public static String getRemoteIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String[] ips = ip.split(",");

        if (ips.length > 1) {
            return ips[0];
        } else {
            return ip;
        }
    }
}