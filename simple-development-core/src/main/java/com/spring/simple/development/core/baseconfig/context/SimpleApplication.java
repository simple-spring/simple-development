package com.spring.simple.development.core.baseconfig.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author liko
 * @Date 2019-09-13 21:15
 * @DESCRIPTION 程序初始化
 */
@Component
public class SimpleApplication implements ApplicationContextAware {

    private static Logger logger = LogManager.getLogger(SimpleApplication.class);

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SimpleApplication.applicationContext = applicationContext;
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

    /**
     * 是否存在bean
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    public static boolean isExistBean(String beanName) throws BeansException {
        return applicationContext.containsBean(beanName);
    }
}