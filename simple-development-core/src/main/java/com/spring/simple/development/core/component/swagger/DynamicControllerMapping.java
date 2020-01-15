package com.spring.simple.development.core.component.swagger;

import com.spring.simple.development.core.init.AppInitializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @ClassName MappingService
 * @Description 动态注入
 * @Author liko
 **/
public class DynamicControllerMapping {

    private static RequestMappingHandlerMapping requestMappingHandlerMapping;

    public static void addMapping(Class loadClass) throws Exception {
        requestMappingHandlerMapping = (RequestMappingHandlerMapping) AppInitializer.rootContext.getBean("requestMappingHandlerMapping");
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)  AppInitializer.rootContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(loadClass);
        String simpleName = loadClass.getSimpleName();
        defaultListableBeanFactory.registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
        Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping, simpleName);
    }

    public void removeMapping(Class beanClass) {
        requestMappingHandlerMapping = (RequestMappingHandlerMapping)  AppInitializer.rootContext.getBean("requestMappingHandlerMapping");
        Object controller =  AppInitializer.rootContext.getBeanNamesForType(beanClass);
        if (controller == null) {
            System.out.println("spring容器中已不存在该实体");
        }
        Class<?> targetClass = controller.getClass();
        ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) {
                Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                try {
                    Method createMappingMethod = RequestMappingHandlerMapping.class.
                            getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                    createMappingMethod.setAccessible(true);
                    RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                            createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                    if (requestMappingInfo != null) {
                        requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
    }
}
