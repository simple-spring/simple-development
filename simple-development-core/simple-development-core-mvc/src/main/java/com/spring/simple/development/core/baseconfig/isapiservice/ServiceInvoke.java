package com.spring.simple.development.core.baseconfig.isapiservice;

import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.req.RpcRequest;
import com.spring.simple.development.core.component.swagger.CodeGenerationHandler;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_FAILED;
import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_NOT_EXIST;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:58
 * @Description 反射调用isConfigApi
 **/
@Component
public class ServiceInvoke {
    private static final Logger logger = LogManager.getLogger(ServiceInvoke.class);

    public ResBody invokeService(RpcRequest request) throws Throwable {
        // check param
        if (StringUtils.isEmpty(request.getMethodName()) || StringUtils.isEmpty(request.getServiceName())) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        logger.info(request.getServiceName() + "-" + request.getMethodName() + " date:" + DateUtils.getCurrentTime());

        // service is exist?
        Object serviceBean = ServerFactory.serviceMap.get(request.getServiceName() + "-" + request.getMethodName());
        if (serviceBean == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        // invoke
        return invokeMethod(request, serviceBean);
    }


    public ResBody invokeConfigService(RpcRequest request) throws Throwable {
        // check param
        if (StringUtils.isEmpty(request.getMethodName()) || StringUtils.isEmpty(request.getServiceName())) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        logger.info(request.getServiceName() + "-" + request.getMethodName() + " date:" + DateUtils.getCurrentTime());

        // service is exist?
        Object serviceBean = ServerFactory.serviceNoLoginMap.get(request.getServiceName() + "-" + request.getMethodName());
        if (serviceBean == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        // invoke
        return invokeMethod(request, serviceBean);
    }

    private ResBody invokeMethod(RpcRequest request, Object serviceBean) throws Throwable {
        try {
            Class<?> serviceClass = serviceBean.getClass();
            String methodName = request.getMethodName();
            FastClass serviceFastClass = FastClass.create(serviceClass);
            // 获取方法上的参数类型
            MethodParams methodParams = ServerFactory.serviceMethodMap.get(request.getServiceName() + "-" + request.getMethodName());
            Class<?>[] parameterTypes = null;
            if (methodParams != null && methodParams.getKey().length >0 ) {
                Class<?>[] classesMethodType = methodParams.getMethodClass();
                parameterTypes = new Class[classesMethodType.length];
                for (int i = 0; i < classesMethodType.length; i++) {
                    parameterTypes[i] = classesMethodType[i];
                }
            }
            // 获取对应的方法
            FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
            Object[] objects = null;
            // 获取请求的参数
            if (request.getReqBody() != null && !CollectionUtils.isEmpty(request.getReqBody().getParamsMap())) {
                String[] keys = methodParams.getKey();
                objects = new Object[keys.length];
                for (int i = 0; i < keys.length; i++) {
                    // 兼容key是原型
                    String key = keys[i];
                    if(CodeGenerationHandler.baseType.get(key) != null){
                        key = CodeGenerationHandler.baseType.get(key);
                    }
                    if (request.getReqBody().getParamsMap().get(key) instanceof JSONObject) {
                        JSONObject jsonObject = (JSONObject) request.getReqBody().getParamsMap().get(key);
                        objects[i] = jsonObject.toJavaObject(parameterTypes[i]);
                    } else {
                        // 兼容swagger类型
                        String paramJson = JSONObject.toJSONString(request.getReqBody().getParamsMap().get(key));
                        objects[i] = JSONObject.parseObject(paramJson, parameterTypes[i]);
                    }
                }
            }
            if (parameterTypes == null && objects != null) {
                throw new GlobalException(SERVICE_FAILED);
            }
            if (parameterTypes != null && objects == null) {
                throw new GlobalException(SERVICE_FAILED);
            }
            Object result = serviceFastMethod.invoke(serviceBean, objects);

            if (result instanceof ResPageDTO) {
                return new ResBody().buildSuccessResBody(null, (ResPageDTO) result, GlobalResponseCode.SYS_SUCCESS);
            }
            return new ResBody().buildSuccessResBody(result, null, GlobalResponseCode.SYS_SUCCESS);
        } catch (Throwable ex) {
            logger.error(DateUtils.getCurrentTime() + "调用" + request.getServiceName() + "的" + request.getMethodName() + "出错:", ex);
            if (ex.getCause() instanceof GlobalException) {
                throw ex.getCause();
            }
            throw ex.getCause();
        }
    }

    /**
     * 旧版api调用
     *
     * @param request
     * @return
     * @throws Throwable
     */
    public ResBody invokeServiceOld(RpcRequest request) throws Throwable {
        // check param
        if (StringUtils.isEmpty(request.getMethodName()) || StringUtils.isEmpty(request.getServiceName())) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        logger.info(request.getServiceName() + "-" + request.getMethodName() + " date:" + DateUtils.getCurrentTime());

        // service is exist?
        Object serviceBean = ServerFactory.serviceMap.get(request.getServiceName() + "-" + request.getMethodName());
        if (serviceBean == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        // invoke
        return invokeMethodOld(request, serviceBean);
    }

    /**
     * 旧版apiConfig调用
     *
     * @param request
     * @return
     * @throws Throwable
     */
    public ResBody invokeConfigServiceOld(RpcRequest request) throws Throwable {
        // check param
        if (StringUtils.isEmpty(request.getMethodName()) || StringUtils.isEmpty(request.getServiceName())) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        logger.info(request.getServiceName() + "-" + request.getMethodName() + " date:" + DateUtils.getCurrentTime());

        // service is exist?
        Object serviceBean = ServerFactory.serviceNoLoginMap.get(request.getServiceName() + "-" + request.getMethodName());
        if (serviceBean == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        // invoke
        return invokeMethodOld(request, serviceBean);
    }

    /**
     * 旧版调用
     *
     * @param request
     * @param serviceBean
     * @return
     * @throws Throwable
     */
    private ResBody invokeMethodOld(RpcRequest request, Object serviceBean) throws Throwable {
        Object result;
        try {
            Class<?> serviceClass = serviceBean.getClass();
            String methodName = request.getMethodName();
            FastClass serviceFastClass = FastClass.create(serviceClass);

            Class<?>[] parameterTypes = new Class[1];
            parameterTypes[0] = ReqBody.class;
            FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);

            Object[] objects = new Object[1];
            if (request.getReqBody() == null) {
                ReqPageDTO page = new ReqPageDTO();
                ReqBody reqBody = new ReqBody();
                reqBody.setPage(page);
                reqBody.setParamsMap(null);
                objects[0] = reqBody;
            } else {
                objects[0] = request.getReqBody();
            }
            result = serviceFastMethod.invoke(serviceBean, objects);
            ResBody resBody = (ResBody) result;
            return resBody;
        } catch (Throwable ex) {
            logger.error(DateUtils.getCurrentTime() + "调用" + request.getServiceName() + "的" + request.getMethodName() + "出错:", ex);
            if (ex.getCause() instanceof GlobalException) {
                throw ex.getCause();
            }
            throw ex.getCause();
        }
    }

}