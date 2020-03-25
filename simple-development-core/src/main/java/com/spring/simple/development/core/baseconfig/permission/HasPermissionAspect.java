package com.spring.simple.development.core.baseconfig.permission;

import com.spring.simple.development.core.annotation.base.HasPermissions;
import com.spring.simple.development.core.commonenum.Logical;
import com.spring.simple.development.core.init.AppInitializer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 拦截权限等目标方法
 *
 * @author
 */
@Order(value = 1)
@Aspect
@Component
public class HasPermissionAspect {
    /**
     * 权限实现
     */
    private HasPermissionService hasPermissionService;

    @Pointcut("@annotation(com.spring.simple.development.core.annotation.base.HasPermissions)")
    public void annotationPointcut() {
    }

    /**
     * @param point
     * @throws Exception 请求前置拦截
     */
    @Before("annotationPointcut()")
    public void before(JoinPoint point) throws Exception {
        // 获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        // 获得访问的方法名
        String methodName = point.getSignature().getName();
        // 得到方法的参数的类型
        Class<?>[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        // 得到访问的方法对象
        Method method = className.getMethod(methodName, argClass);
        method.setAccessible(true);
        // 判断是否存在@Valid注解
        if (method.isAnnotationPresent(HasPermissions.class)) {
            HasPermissions hasPermissions = method.getAnnotation(HasPermissions.class);

            String[] keys = hasPermissions.value();
            Logical logical = hasPermissions.LOGICAL();
            getHasPermissionService().checkPermission(keys, logical);
        }
    }

    private HasPermissionService getHasPermissionService() {
        if (hasPermissionService == null) {
            if(AppInitializer.rootContext.containsBean("hasPermissionService") == false){
                throw new RuntimeException("没有权限实现");
            }
            HasPermissionService hasPermissionServiceBean = AppInitializer.rootContext.getBean(HasPermissionService.class);
            if (hasPermissionServiceBean == null) {
                throw new RuntimeException("没有权限实现");
            }
            hasPermissionService = hasPermissionServiceBean;
        }
        return hasPermissionService;
    }
}
