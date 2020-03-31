package com.spring.simple.development.support.constant;

/**
 * 系统变量
 *
 * @author liko wang
 */
public class SystemProperties {

    /**
     * spring simple基础组件包路径
     */
    public static final String SPRING_SIMPLE_BASE_COMPONENT_PACKAGE_PATH = "com.spring.simple.development.core.baseconfig";

    /**
     * spring simple组件包路径
     */
    public static final String SPRING_SIMPLE_COMPONENT_PACKAGE_PATH = "com.spring.simple.development.core.component";

    /**
     * 程序入口类全称
     */
    public static final String SPRING_APPLICATION_CLASS_NAME = "spring.application.class.name";

    /**
     * 组件方法名
     */
    public static final String CONFIG_METHOD_NAME = "getConfigClass";

    /**
     * 基本组件
     */
    public static final String APPLICATION_ROOT_CONFIG_NAME = "spring.simple.root.name";
    public static final String APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME = "spring.simple.root.appPackagePathName";


    /**
     * mvc组件
     */
    public static final String APPLICATION_MVC_CONFIG_URL_PATH = "spring.simple.mvc.urlPath";
    public static final String APPLICATION_MVC_CONFIG_PACKAGE_PATH = "spring.simple.mvc.urlPath.packagePath";
    public static final String APPLICATION_MVC_CONFIG_INTERCEPTOR_PATH = "spring.simple.mvc.urlPath.interceptorPath";

    /**
     * mybatis组件
     */
    public static final String APPLICATION_MYBATIS_CONFIG_MAPPER_PATH = "spring.simple.mybatis.mapperPath";
    public static final String APPLICATION_MYBATIS_CONFIG_MODEL_PATH = "spring.simple.mybatis.modelPath";
    public static final String APPLICATION_MYBATIS_CONFIG_MAPPER_XML_PATH = "spring.simple.mybatis.mapperXmlPath";
    public static final String APPLICATION_MYBATIS_CONFIG_EXPRESSION = "spring.simple.mybatis.expression";

    /**
     * dubbo组件
     */
    public static final String APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE = "spring.simple.dubbo.dubboPackage";
    public static final String APPLICATION_DUBBO_CONFIG_APPLICATION_NAME = "spring.simple.dubbo.application.name";
    public static final String APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS = "spring.simple.dubbo.registry.address";
    public static final String APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME = "spring.simple.dubbo.protocol.name";
    public static final String APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT = "spring.simple.dubbo.protocol.port";

    /**
     * elasticsearch组件
     */
    public static final String APPLICATION_ELASTICSEARCH_HOST = "spring.simple.elasticsearch.host";
    public static final String APPLICATION_ELASTICSEARCH_PORT = "spring.simple.elasticsearch.port";
    public static final String APPLICATION_ELASTICSEARCH_CLUSTER_NAME = "spring.simple.elasticsearch.cluster.name";

    /**
     * job组件
     */
    public static final String APPLICATION_XXLJOB_CONFIG_JOBPACKAGE = "spring.simple.xxljob.jobPackage";
    public static final String APPLICATION_XXLJOB_CONFIG_ADDRESSES = "spring.simple.xxljob.addresses";
    public static final String APPLICATION_XXLJOB_CONFIG_APPNAME = "spring.simple.xxljob.appName";
    public static final String APPLICATION_XXLJOB_CONFIG_IP = "spring.simple.xxljob.ip";
    public static final String APPLICATION_XXLJOB_CONFIG_PORT = "spring.simple.xxljob.port";
    public static final String APPLICATION_XXLJOB_CONFIG_ACCESSTOKEN = "spring.simple.xxljob.accessToken";
    public static final String APPLICATION_XXLJOB_CONFIG_LOGPATH = "spring.simple.xxljob.logPath";
    public static final String APPLICATION_XXLJOB_CONFIG_LOGRETENTIONDAYS = "spring.simple.xxljob.logRetentionDays";
    /**
     * swagger组件
     **/
    public static final String APPLICATION_SWAGGER_TITLE = "spring.simple.swagger.title";
    public static final String APPLICATION_SWAGGER_DESCRIPTION = "spring.simple.swagger.description";
    public static final String APPLICATION_SWAGGER_CONTACT = "spring.simple.swagger.contact";
    public static final String APPLICATION_SWAGGER_VERSION = "spring.simple.swagger.version";
    public static final String APPLICATION_SWAGGER_URL = "spring.simple.swagger.url";
    public static final String APPLICATION_SWAGGER_IS_ENABLE = "spring.simple.swagger.is_enable";
    public static final String APPLICATION_SWAGGER_HEADER_PARAMS = "spring.simple.swagger.header.params";
    public static final String APPLICATION_SWAGGER_HEADER_DESCRIPTION = "spring.simple.swagger.header.description";



    /**
     * cassandra组件
     */
    public static final String APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE = "spring.simple.cassandra.cassandraPackage";
    public static final String APPLICATION_CASSANDRA_CONFIG_CASSANDRA_NAME= "spring.simple.cassandra.cassandraName";
    public static final String APPLICATION_CASSANDRA_CONFIG_ADDRESS = "spring.simple.cassandra.address";
    public static final String APPLICATION_CASSANDRA_CONFIG_PORT = "spring.simple.cassandra.port";
    public static final String APPLICATION_CASSANDRA_CONFIG_KEY_SPACES_NAME = "spring.simple.cassandra.keySpaces.name";
    public static final String APPLICATION_CASSANDRA_CONFIG_USERNAME = "spring.simple.cassandra.userName";
    public static final String APPLICATION_CASSANDRA_CONFIG_PASSWORD = "spring.simple.cassandra.password";

    /**
     * alert 组件
     **/
    public static final String APPLICATION_ALERT_CONFIG_COLLECTION_URL = "spring.simple.alert.url";
    public static final String APPLICATION_ALERT_CONFIG_APPLICATION_CODE = "spring.simple.alert.applicationCode";
    public static final String APPLICATION_ALERT_CONFIG_APPLICATION_TOKEN = "spring.simple.alert.applicationToken";
    public static final String APPLICATION_ALERT_CONFIG_APPLICATION_LOG_PATH = "spring.simple.alert.logPath";

}
