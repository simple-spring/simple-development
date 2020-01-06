package com.spring.simple.development.demo.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author liko
 * @Date 2019-09-13 21:15
 * @DESCRIPTION TODO
 */
@Component
@Configuration
public class DemoApplication implements ApplicationContextAware {

    private static Logger logger = LogManager.getLogger(DemoApplication.class);

    private static ApplicationContext applicationContext;
//
//    @Bean(name = "lavaPvgInfo")
//    public PrivilegeInfo privilegeInfo() {
//        return new PrivilegeInfo();
//    }
//    @Bean
//    public BizExceptions bizExceptions() {
//        return new BizExceptions();
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("程序入口");
        DemoApplication.applicationContext = applicationContext;
    }

    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBeanByType(Class clazz) throws BeansException {
        return (T) applicationContext.getBean(clazz);
    }
}