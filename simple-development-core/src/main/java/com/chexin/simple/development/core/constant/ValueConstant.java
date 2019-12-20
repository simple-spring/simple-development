package com.chexin.simple.development.core.constant;


import com.chexin.simple.development.core.properties.PropertyConfigurer;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:19
 * @Description //TODO
 **/
public class ValueConstant {

    public static String dbUrl = PropertyConfigurer.getProperty("spring.datasource.url");

    public static String username = PropertyConfigurer.getProperty("spring.datasource.username");

    public static String password = PropertyConfigurer.getProperty("spring.datasource.password");

    public static String driverClassName = PropertyConfigurer.getProperty("spring.datasource.driverClassName");

    public static int initialSize = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.initialSize")).intValue();

    public static int minIdle = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.minIdle")).intValue();

    public static int maxActive = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.maxActive")).intValue();

    public static int maxWait = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.maxWait")).intValue();

    public static int timeBetweenEvictionRunsMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public static int minEvictableIdleTimeMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.datasource.minEvictableIdleTimeMillis")).intValue();

    public static String validationQuery = PropertyConfigurer.getProperty("spring.datasource.validationQuery");

    public static boolean testWhileIdle = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testWhileIdle"));

    public static boolean testOnBorrow = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testOnBorrow"));

    public static boolean testOnReturn = Boolean.getBoolean(PropertyConfigurer.getProperty("spring.datasource.testOnReturn"));
}