package com.chexin.simple.development.core.constant;

/**
 * 系统变量
 */
public class SystemProperties {

    /**
     * 程序入口类全称
     */
    public static final String SPRINGAPPLICATION_CLASS_NAME = "spring.application.class.name";

    /**
     * 组件方法名
     */
    public static final String CONFIGMETHODNAME = "getConfigClass";

    /**
     * 基本组件
     */
    public static final String APPLICATION_ROOT_CONFIG = "spring.simple.root";
    /**
     * mvc组件
     */
    public static final String APPLICATION_MVC_CONFIG = "spring.simple.mvc";
    /**
     * mybatis组件
     */
    public static final String APPLICATION_MYBATIS_CONFIG = "spring.simple.mybatis";
    /**
     * dubbo组件
     */
    public static final String APPLICATION_DUBBO_CONFIG = "spring.simple.dubbo";
    /**
     * redis组件
     */
    public static final String APPLICATION_REDIS_CONFIG = "spring.simple.redis";


    /**
     * 基本组件
     */
    public static final String APPLICATION_ROOT_CONFIG_NAME = "spring.simple.root.name";
    public static final String APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME = "spring.simple.root.appPackagePathName";


    /**
     * mvc组件
     */
    public static final String APPLICATION_MVC_CONFIG_URLPATH = "spring.simple.mvc.urlPath";
    public static final String APPLICATION_MVC_CONFIG_PACKAGEPATH = "spring.simple.mvc.urlPath.packagePath";
    public static final String APPLICATION_MVC_CONFIG_INTERCEPTORPATH = "spring.simple.mvc.urlPath.interceptorPath";

    /**
     * mybatis组件
     */
    public static final String APPLICATION_MYBATIS_CONFIG_MAPPERPATH = "spring.simple.mybatis.mapperPath";
    public static final String APPLICATION_MYBATIS_CONFIG_MODELPATH = "spring.simple.mybatis.modelPath";
    public static final String APPLICATION_MYBATIS_CONFIG_MAPPERXMLPATH = "spring.simple.mybatis.mapperXmlPath";
    public static final String APPLICATION_MYBATIS_CONFIG_EXPRESSION = "spring.simple.mybatis.expression";

    /**
     * dubbo组件
     */
    public static final String APPLICATION_DUBBO_CONFIG_DUBBOPACKAGE = "spring.simple.dubbo.dubboPackage";
    public static final String APPLICATION_DUBBO_CONFIG_APPLICATION_NAME = "spring.simple.dubbo.application.name";
    public static final String APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS = "spring.simple.dubbo.registry.address";
    public static final String APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME = "spring.simple.dubbo.protocol.name";
    public static final String APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT = "spring.simple.dubbo.protocol.port";
}
