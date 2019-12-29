package com.spring.simple.development.core.baseconfig.isapiservice;

import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.support.utils.AopTargetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * ServerFactory
 *
 * @author liko
 */
public class ServerFactory {
    private static final Logger logger = LogManager.getLogger(ServerFactory.class);

    public static transient ApplicationContext applicationContext;

    /**
     * init  service map
     */
    public static Map<String, Object> serviceMap = new HashMap<>();

    /**
     * init  serviceNoLogin map
     */
    public static Map<String, Object> serviceNoLoginMap = new HashMap<>();

    public static void putService(Object serviceBean) throws Exception {
        IsApiService isApiService = AopTargetUtils.getTarget(serviceBean).getClass().getAnnotation(IsApiService.class);
        String serviceName = isApiService.value();
        if (StringUtils.isEmpty(serviceName)) {
            serviceName = AopTargetUtils.getTarget(serviceBean).getClass().getInterfaces()[0].getSimpleName();
        }
        System.err.println("服务:" + serviceName + "已加载");

        Method[] methods = AopTargetUtils.getTarget(serviceBean).getClass().getDeclaredMethods();
        if (methods.length == 0) {
            throw new RuntimeException(AopTargetUtils.getTarget(serviceBean).getClass() + "接口实现方法为空");
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(NoApiMethod.class) == false) {
                String methodName = "";
                IsApiMethodService isApiMethodService = method.getAnnotation(IsApiMethodService.class);
                if (isApiMethodService == null) {
                    methodName = method.getName();
                } else {
                    methodName = isApiMethodService.value();
                }
                if (isApiService.isLogin()) {
                    serviceNoLoginMap.put(serviceName + "-" + methodName, serviceBean);
                } else {
                    serviceMap.put(serviceName + "-" + methodName, serviceBean);
                }
            }
        }
    }
}
