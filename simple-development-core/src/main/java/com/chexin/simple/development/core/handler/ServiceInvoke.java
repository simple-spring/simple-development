package com.chexin.simple.development.core.handler;

import com.chexin.simple.development.core.commonenum.Logical;
import com.chexin.simple.development.core.mvc.page.ReqPageDTO;
import com.chexin.simple.development.core.mvc.req.ReqBody;
import com.chexin.simple.development.core.mvc.req.RpcRequest;
import com.chexin.simple.development.core.mvc.res.ResBody;
import com.chexin.simple.development.support.utils.DateUtils;
import com.chexin.simple.development.support.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.chexin.simple.development.support.GlobalResponseCode.SERVICE_NOT_EXIST;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:58
 * @Description //TODO
 **/
@Component
public class ServiceInvoke {
    private static final Logger logger = LogManager.getLogger(ServiceInvoke.class);

    public ResBody invokeService(RpcRequest request, HttpServletRequest httpServletRequest) throws Throwable {
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
        // check permission
        String[] permissions = ServerFactory.hasPermissionMap.get(request.getServiceName() + "-" + request.getMethodName());
        Logical logical = ServerFactory.relationPermissionMap.get(request.getServiceName() + "-" + request.getMethodName());
        ServerFactory.checkPermission(permissions, logical);

        // invoke
        return invokeMethod(request, serviceBean);
    }


    public ResBody invokeConfigService(RpcRequest request, HttpServletRequest httpServletRequest) throws Throwable {
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
        // check permission
        String[] permissions = ServerFactory.hasPermissionMap.get(request.getServiceName() + "-" + request.getMethodName());
        Logical logical = ServerFactory.relationPermissionMap.get(request.getServiceName() + "-" + request.getMethodName());
        ServerFactory.checkPermission(permissions, logical);

        // invoke
        return invokeMethod(request, serviceBean);
    }

    private ResBody invokeMethod(RpcRequest request, Object serviceBean) throws Throwable {
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