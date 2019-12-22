package com.chexin.simple.development.core.constant;


import com.chexin.simple.development.support.properties.PropertyConfigurer;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:19
 * @Description //TODO
 **/
public class ValueConstant {

    public final static String dbUrl = PropertyConfigurer.getProperty("spring.datasource.url");

    public final static String username = PropertyConfigurer.getProperty("spring.datasource.username");

    public final static String password = PropertyConfigurer.getProperty("spring.datasource.password");

    public final static String driverClassName = PropertyConfigurer.getProperty("spring.datasource.driverClassName");

    public final static int initialSize = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.initialSize")).intValue();

    public final static int minIdle = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.minIdle")).intValue();

    public final static int maxActive = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.maxActive")).intValue();

    public final static int maxWait = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.maxWait")).intValue();

    public final static int timeBetweenEvictionRunsMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public final static int minEvictableIdleTimeMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.minEvictableIdleTimeMillis")).intValue();

    public final static String validationQuery = PropertyConfigurer.getProperty("spring.datasource.validationQuery");

    public final static boolean testWhileIdle = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testWhileIdle"));

    public final static boolean testOnBorrow = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testOnBorrow"));

    public final static boolean testOnReturn = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testOnReturn"));
}