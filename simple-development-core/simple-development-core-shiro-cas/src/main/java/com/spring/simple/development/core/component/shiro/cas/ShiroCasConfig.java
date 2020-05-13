package com.spring.simple.development.core.component.shiro.cas;

import com.acl.support.auth.web.authz.DefaultBizAuthorizationSupporter;
import com.acl.support.auth.web.mgt.DefaultBizWebSecurityManager;
import com.acl.xauth.web.filter.XauthFilterFactoryBean;
import com.spring.simple.development.core.handler.listener.SimpleApplicationListener;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * @author liko.wang
 * @Date 2020/4/10/010 16:39
 * @Description shiro cas 实现
 **/
@Configuration
@ComponentScan(basePackages = "com.acl.support.auth.web.authz")
public class ShiroCasConfig implements SimpleApplicationListener {
    public ShiroCasConfig() {
        System.out.println("shiro cas init");
    }

    @Bean
    public DefaultBizAuthorizationSupporter getDefaultBizAuthorizationSupporter(){
        return new DefaultBizAuthorizationSupporter();
    }

    @Bean
    public ShiroLavaSupportInterceptor getShiroLavaSupportInterceptor() {
        return new ShiroLavaSupportInterceptor();
    }

    @Bean
    public DelegatingFilterProxy getDelegatingFilterProxy() {
        return new DelegatingFilterProxy();
    }

    @Bean(value = "shiroFilter")
    public XauthFilterFactoryBean getXauthFilterFactoryBean() {
        return new XauthFilterFactoryBean();
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_HOST));
        jedisConnectionFactory.setPort(Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_PORT)));
        jedisConnectionFactory.setDatabase(Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_DATABASE)));
        jedisConnectionFactory.setPassword(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_PASSWORD));
        return jedisConnectionFactory;

    }

    @Bean(value = "securityManager")
    public DefaultBizWebSecurityManager getDefaultBizWebSecurityManager() {

        // CAS 监听器
        AppInitializer.servletContext.addListener(SingleSignOutHttpSessionListener.class);

        // shiro 过滤
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        FilterRegistration.Dynamic shiroFilter = AppInitializer.servletContext.addFilter("shiroFilter", delegatingFilterProxy);
        //配置mapping
        //REQUEST：普通模式，来自客户端的请求,ASYNC:来自AsyncContext的异步请求
        // false则是在web.xml定义的filter后代码设置的
        shiroFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/*");

        // cas 过滤
        FilterRegistration.Dynamic casFilter = AppInitializer.servletContext.addFilter("CAS Single Sign Out Filter", SingleSignOutFilter.class);
        //配置mapping
        casFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/shiro-cas");


        DefaultBizWebSecurityManager defaultBizWebSecurityManager = new DefaultBizWebSecurityManager();
        defaultBizWebSecurityManager.setSkipAuthenticate(Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SKIPAUTHENTICATE)));
        defaultBizWebSecurityManager.setSuccessUrl(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SUCCESSURL));
        defaultBizWebSecurityManager.setUnauthorizedUrl(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_UNAUTHORIZEDURL));
        defaultBizWebSecurityManager.setBizSiteUrlPrefix(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_BIZSITEURLPREFIX));
        defaultBizWebSecurityManager.setCasServerUrlPrefix(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_CASSERVERURLPREFIX));
        defaultBizWebSecurityManager.setRedisConnectionFactory(getJedisConnectionFactory());
        return defaultBizWebSecurityManager;
    }

//    @Bean
//    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
//        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//        methodInvokingFactoryBean.setArguments(getDefaultBizWebSecurityManager());
//        return methodInvokingFactoryBean;
//    }

    @Override
    public void onApplicationEvent(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {

    }
}