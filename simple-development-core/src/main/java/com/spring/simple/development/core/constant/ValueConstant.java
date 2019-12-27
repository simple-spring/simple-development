package com.spring.simple.development.core.constant;


import com.spring.simple.development.support.properties.PropertyConfigurer;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:19
 * @Description //TODO
 **/
public class ValueConstant {

    public final static String dbUrl = PropertyConfigurer.getProperty("spring.simple.datasource.url");

    public final static String username = PropertyConfigurer.getProperty("spring.simple.datasource.username");

    public final static String password = PropertyConfigurer.getProperty("spring.simple.datasource.password");

    public final static String driverClassName = PropertyConfigurer.getProperty("spring.simple.datasource.driverClassName");

    public final static int initialSize = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.initialSize")).intValue();

    public final static int minIdle = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.minIdle")).intValue();

    public final static int maxActive = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.maxActive")).intValue();

    public final static int maxWait = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.maxWait")).intValue();

    public final static int timeBetweenEvictionRunsMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public final static int minEvictableIdleTimeMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.minEvictableIdleTimeMillis")).intValue();

    public final static String validationQuery = PropertyConfigurer.getProperty("spring.simple.datasource.validationQuery");

    public final static boolean testWhileIdle = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testWhileIdle"));

    public final static boolean testOnBorrow = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnBorrow"));

    public final static boolean testOnReturn = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnReturn"));
}