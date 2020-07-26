package com.spring.simple.development.core.baseconfig.isapiservice;

import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.support.utils.AopTargetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /**
     * init  service map
     */
    public static Map<String, Object> serviceMap = new HashMap<>();

    /**
     * init  serviceNoLogin map
     */
    public static Map<String, Object> serviceNoLoginMap = new HashMap<>();

    /**
     * init  serviceMethod map
     */
    public static Map<String, MethodParams> serviceMethodMap = new HashMap<>();

    public static void putService(Object serviceBean) throws Exception {
        String serviceName;
        if(AopTargetUtils.getTarget(serviceBean).getClass().getInterfaces().length == 0){
            serviceName = AopTargetUtils.getTarget(serviceBean).getClass().getSimpleName();
        }else{
            serviceName = AopTargetUtils.getTarget(serviceBean).getClass().getInterfaces()[0].getSimpleName();
        }
        System.err.println("服务:" + serviceName + "已加载");

        Method[] methods = AopTargetUtils.getTarget(serviceBean).getClass().getDeclaredMethods();
        if (methods.length == 0) {
            return;
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(IsApiService.class)) {
                String methodName = "";
                IsApiService isApiService = method.getAnnotation(IsApiService.class);

                if (StringUtils.isNotEmpty(isApiService.serviceName())) {
                    serviceName = isApiService.serviceName();
                }
                if (StringUtils.isEmpty(isApiService.methodName())) {
                    methodName = method.getName();
                } else {
                    methodName = isApiService.methodName();
                }
                if (isApiService.isLogin()) {
                    serviceMap.put(serviceName + "-" + methodName, serviceBean);
                } else {
                    serviceNoLoginMap.put(serviceName + "-" + methodName, serviceBean);
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null) {
                    String[] paramsKey = new String[parameterTypes.length];
                    Class<?>[] methodClass = new Class[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        String simpleName = parameterTypes[i].getSimpleName();
                        String paramKey = toLowerCaseFirstOne(simpleName);
                        paramsKey[i] = paramKey;
                        methodClass[i] = parameterTypes[i];
                    }
                    MethodParams methodParams = new MethodParams();
                    methodParams.setKey(paramsKey);
                    methodParams.setMethodClass(methodClass);
                    serviceMethodMap.put(serviceName + "-" + methodName, methodParams);
                }
            }
        }
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
