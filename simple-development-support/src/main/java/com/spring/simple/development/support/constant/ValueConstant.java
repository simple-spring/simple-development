package com.spring.simple.development.support.constant;


import com.spring.simple.development.support.properties.PropertyConfigurer;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 14:19
 * @Description //TODO
 **/
public class ValueConstant {

    public  static String dbUrl = PropertyConfigurer.getProperty("spring.simple.datasource.url");

    public  static String username = PropertyConfigurer.getProperty("spring.simple.datasource.username");

    public  static String password = PropertyConfigurer.getProperty("spring.simple.datasource.password");

    public  static String driverClassName = PropertyConfigurer.getProperty("spring.simple.datasource.driverClassName");

    public  static int initialSize = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.initialSize")).intValue();

    public  static int minIdle = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.minIdle")).intValue();

    public  static int maxActive = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.maxActive")).intValue();

    public  static int maxWait = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.maxWait")).intValue();

    public  static int timeBetweenEvictionRunsMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public  static int minEvictableIdleTimeMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.datasource.minEvictableIdleTimeMillis")).intValue();

    public  static String validationQuery = PropertyConfigurer.getProperty("spring.simple.datasource.validationQuery");

    public  static boolean testWhileIdle = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testWhileIdle"));

    public  static boolean testOnBorrow = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnBorrow"));

    public  static boolean testOnReturn = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.datasource.testOnReturn"));


    // 从库
    public  static boolean is_open_slave = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.is.open.slave.datasource"));

    public  static String slave_dbUrl = PropertyConfigurer.getProperty("spring.simple.slave.datasource.url");

    public  static String slave_username = PropertyConfigurer.getProperty("spring.simple.slave.datasource.username");

    public  static String slave_password = PropertyConfigurer.getProperty("spring.simple.slave.datasource.password");

    public  static String slave_driverClassName = PropertyConfigurer.getProperty("spring.simple.slave.datasource.driverClassName");

    public  static int slave_initialSize = PropertyConfigurer.getProperty("spring.simple.slave.datasource.initialSize") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.initialSize")).intValue();

    public  static int slave_minIdle = PropertyConfigurer.getProperty("spring.simple.slave.datasource.minIdle") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.minIdle")).intValue();

    public  static int slave_maxActive = PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxActive") == null ? 0 : Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxActive")).intValue();

    public  static int slave_maxWait = PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxWait") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.maxWait")).intValue();

    public  static int slave_timeBetweenEvictionRunsMillis = PropertyConfigurer.getProperty("spring.simple.slave.datasource.timeBetweenEvictionRunsMillis") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.timeBetweenEvictionRunsMillis")).intValue();

    public  static int slave_minEvictableIdleTimeMillis = PropertyConfigurer.getProperty("spring.simple.slave.datasource.minEvictableIdleTimeMillis") == null?0:Long.valueOf(PropertyConfigurer.getProperty("spring.simple.slave.datasource.minEvictableIdleTimeMillis")).intValue();

    public  static String slave_validationQuery = PropertyConfigurer.getProperty("spring.simple.slave.datasource.validationQuery");

    public  static boolean slave_testWhileIdle = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testWhileIdle"));

    public  static boolean slave_testOnBorrow = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testOnBorrow"));

    public  static boolean slave_testOnReturn = Boolean.parseBoolean(PropertyConfigurer.getProperty("spring.simple.slave.datasource.testOnReturn"));
}