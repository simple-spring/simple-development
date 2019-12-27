package com.spring.simple.development.core.baseconfig.isapiservice;

import com.alibaba.fastjson.JSON;
import com.spring.simple.development.core.commonenum.Logical;
import com.spring.simple.development.core.annotation.base.HasPermissions;
import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.baseconfig.permission.HasPermissionService;
import com.spring.simple.development.support.utils.AopTargetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
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
    /**
     * 权限集合
     */
    public static Map<String, String[]> hasPermissionMap = new HashMap<>();
    /**
     * 权限关系集合
     */
    public static Map<String, Logical> relationPermissionMap = new HashMap<>();

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

    /**
     * 加载权限key
     *
     * @param method
     * @throws Exception
     */
    public static void putPermission(Method method) throws Exception {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            // 获取注解的具体类型
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (HasPermissions.class == annotationType) {
                HasPermissions hasPermissions = (HasPermissions) annotation;
                String[] value = hasPermissions.value();
                Logical logical = hasPermissions.LOGICAL();
                // 类名+方法名
                String name = method.getDeclaringClass().getInterfaces()[0].getSimpleName() + "-" + method.getName();
                if (hasPermissionMap.containsKey(name)) {
                    throw new RuntimeException("api方法名" + name + "的权限已存在,权限注解的方法名必须唯一");
                }
                // 是否加载
                if (value.length == 0) {
                    throw new RuntimeException("api方法名" + name + "的权限名为空");
                }
                hasPermissionMap.put(name, value);
                relationPermissionMap.put(name, logical);
                System.err.println("api方法权限:" + name + "的" + JSON.toJSONString(value) + "已加载");
            }

        }
    }

    public static Boolean isExist(String key) {
        return serviceMap.containsKey(key);
    }

    /**
     * 校验权限并返回子权限
     *
     * @param logical
     * @param permissions
     */
    public static void checkPermission(String[] permissions, Logical logical) {

        if (permissions == null) {
            return;
        }
        if (permissions.length == 0) {
            return;
        }
        HasPermissionService hasPermissionService = applicationContext.getBean(HasPermissionService.class);
        if (hasPermissionService == null) {
            throw new RuntimeException("权限实现找不到");
        }
        // 校验权限
        hasPermissionService.checkPermission(permissions, logical);
    }
}
