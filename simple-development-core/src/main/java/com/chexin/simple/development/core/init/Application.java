package com.chexin.simple.development.core.init;

import com.chexin.simple.development.core.properties.PropertyConfigurer;
import com.chexin.simple.development.core.utils.JedisPoolUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author liko
 * @Date 2019-09-13 21:15
 * @DESCRIPTION 程序入口
 */
@Component
public class Application implements ApplicationContextAware {

    private static Logger logger = LogManager.getLogger(Application.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("程序入口");
        Application.applicationContext = applicationContext;
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