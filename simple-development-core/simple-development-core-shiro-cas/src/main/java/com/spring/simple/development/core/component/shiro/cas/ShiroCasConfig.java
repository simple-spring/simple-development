package com.spring.simple.development.core.component.shiro.cas;

import com.jc.support.auth.web.authz.DefaultBizAuthorizationSupporter;
import com.jc.support.auth.web.mgt.DefaultBizWebSecurityManager;
import com.jc.xauth.web.filter.XauthFilterFactoryBean;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author liko.wang
 * @Date 2020/4/10/010 16:39
 * @Description shiro cas 实现
 **/
@Configuration
public class ShiroCasConfig {
    public ShiroCasConfig() {
        System.out.println("shiro cas init");
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
        DefaultBizWebSecurityManager defaultBizWebSecurityManager = new DefaultBizWebSecurityManager();
        defaultBizWebSecurityManager.setSkipAuthenticate(Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SKIPAUTHENTICATE)));
        defaultBizWebSecurityManager.setSuccessUrl(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SUCCESSURL));
        defaultBizWebSecurityManager.setUnauthorizedUrl(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_UNAUTHORIZEDURL));
        defaultBizWebSecurityManager.setBizSiteUrlPrefix(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_BIZSITEURLPREFIX));
        defaultBizWebSecurityManager.setCasServerUrlPrefix(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_CASSERVERURLPREFIX));
        defaultBizWebSecurityManager.setRedisConnectionFactory(getJedisConnectionFactory());
        return defaultBizWebSecurityManager;
    }

    @Bean
    public DefaultBizAuthorizationSupporter getDefaultBizAuthorizationSupporter() {
        return new DefaultBizAuthorizationSupporter();
    }

    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(getDefaultBizWebSecurityManager());
        return methodInvokingFactoryBean;
    }
}