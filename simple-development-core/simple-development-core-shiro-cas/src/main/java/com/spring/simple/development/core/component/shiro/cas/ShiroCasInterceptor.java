package com.spring.simple.development.core.component.shiro.cas;

import com.acl.support.auth.web.interceptor.DefaultBizAuthenticationHandlerInterceptor;
import com.acl.xauth.web.interceptor.AuthenticationHandlerInterceptor;
import com.spring.simple.development.core.init.AppInitializer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class ShiroCasInterceptor {
    private DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) AppInitializer.rootContext.getAutowireCapableBeanFactory();

    public DefaultBizAuthenticationHandlerInterceptor getDefaultBizAuthenticationHandlerInterceptor() {
        // 解决自定义拦截器中无法注入bean
        //创建bean信息
        BeanDefinitionBuilder definitionBuilderDefaultBizAuthenticationHandlerInterceptor = BeanDefinitionBuilder.genericBeanDefinition(DefaultBizAuthenticationHandlerInterceptor.class);
        //动态注册bean
        defaultListableBeanFactory.registerBeanDefinition(DefaultBizAuthenticationHandlerInterceptor.class.getSimpleName(), definitionBuilderDefaultBizAuthenticationHandlerInterceptor.getBeanDefinition());

        // 获取bean
        DefaultBizAuthenticationHandlerInterceptor beanDefaultBizAuthenticationHandlerInterceptor = (DefaultBizAuthenticationHandlerInterceptor) AppInitializer.rootContext.getBean(DefaultBizAuthenticationHandlerInterceptor.class.getSimpleName());
        return beanDefaultBizAuthenticationHandlerInterceptor;
    }

    public AuthenticationHandlerInterceptor getAuthenticationHandlerInterceptor() {
        // 解决自定义拦截器中无法注入bean
        //创建bean信息
        BeanDefinitionBuilder definitionBuilderAuthenticationHandlerInterceptor = BeanDefinitionBuilder.genericBeanDefinition(AuthenticationHandlerInterceptor.class);
        //动态注册bean
        defaultListableBeanFactory.registerBeanDefinition(AuthenticationHandlerInterceptor.class.getSimpleName(), definitionBuilderAuthenticationHandlerInterceptor.getBeanDefinition());
        AuthenticationHandlerInterceptor beanAuthenticationHandlerInterceptor = (AuthenticationHandlerInterceptor) AppInitializer.rootContext.getBean(AuthenticationHandlerInterceptor.class.getSimpleName());
        return beanAuthenticationHandlerInterceptor;
    }
}
