package com.spring.simple.development.support.constant;


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

    public final static boolean testWhileIdle = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testWhileIdle"));

    public final static boolean testOnBorrow = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnBorrow"));

    public final static boolean testOnReturn = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnReturn"));


    // 从库
    public final static boolean is_open_slave = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.is.open.slave.datasource"));

    public final static String slave_dbUrl = PropertyConfigurer.getProperty("spring.simple.slave.datasource.url");

    public final static String slave_username = PropertyConfigurer.getProperty("spring.simple.slave.datasource.username");

    public final static String slave_password = PropertyConfigurer.getProperty("spring.simple.slave.datasource.password");

    public final static String slave_driverClassName = PropertyConfigurer.getProperty("spring.simple.slave.datasource.driverClassName");

    public final static int slave_initialSize = PropertyConfigurer.getProperty("spring.simple.slave.datasource.initialSize") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.initialSize")).intValue();

    public final static int slave_minIdle = PropertyConfigurer.getProperty("spring.simple.slave.datasource.minIdle") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.minIdle")).intValue();

    public final static int slave_maxActive = PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxActive") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxActive")).intValue();

    public final static int slave_maxWait = PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxWait") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxWait")).intValue();

    public final static int slave_timeBetweenEvictionRunsMillis = PropertyConfigurer.getProperty("spring.simple.slave.datasource.timeBetweenEvictionRunsMillis") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public final static int slave_minEvictableIdleTimeMillis = PropertyConfigurer.getProperty("spring.simple.slave.datasource.minEvictableIdleTimeMillis") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.minEvictableIdleTimeMillis")).intValue();

    public final static String slave_validationQuery = PropertyConfigurer.getProperty("spring.simple.slave.datasource.validationQuery");

    public final static boolean slave_testWhileIdle = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testWhileIdle"));

    public final static boolean slave_testOnBorrow = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testOnBorrow"));

    public final static boolean slave_testOnReturn = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testOnReturn"));
}