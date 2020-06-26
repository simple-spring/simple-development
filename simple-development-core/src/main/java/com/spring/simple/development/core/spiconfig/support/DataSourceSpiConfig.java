package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableMybatis;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableMybatis")
public class DataSourceSpiConfig implements SimpleSpiConfig<EnableMybatis> {
    public static List<String> mapperPackageNames = new ArrayList<>();
    @Override
    public Class getConfigClass(EnableMybatis enableMybatis) {
        try {
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            // mapper xml路径
            String defaultMapperXmlPath = "classpath:mybatis/*/*.xml";
            if (StringUtils.isEmpty(enableMybatis.mapperXmlPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_XML_PATH, defaultMapperXmlPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_XML_PATH, enableMybatis.mapperXmlPath());
            }

            // model 路径
            String defaultModelPath = basePackageName + PackageNameConstant.MODEL;
            if (StringUtils.isEmpty(enableMybatis.modelPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODEL_PATH, defaultModelPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODEL_PATH, enableMybatis.modelPath());
            }
            // 事务表达式
            String defaultExpression = "execution (* " + basePackageName + PackageNameConstant.SERVICE + "..*.*(..))";
            if (StringUtils.isEmpty(enableMybatis.expression())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_EXPRESSION, defaultExpression);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_EXPRESSION, enableMybatis.expression());
            }
            // mapper 路径
            String defaultMapperPath = basePackageName + PackageNameConstant.MAPPER;
            if (StringUtils.isEmpty(enableMybatis.mapperPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_PATH, defaultMapperPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_PATH, enableMybatis.mapperPath());
            }
            // 添加DataSourceConfig MapperScan扫描包的路径
            mapperPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_PATH));
            // 是否打开数据转换工具
            boolean isEnableDataPrecess = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_DATA_PROCESS));
            if (isEnableDataPrecess) {
                mapperPackageNames.add("com.spring.simple.development.core.component.data.process.executor.mapper");
            }
            Class<?> targetClass = Class.forName("com.spring.simple.development.core.component.jdbc.DataSourceConfig");
            Class dataSourceConfigClass = ClassLoadUtil.javassistCompile(targetClass, "org.mybatis.spring.annotation.MapperScan", mapperPackageNames, "basePackages");

            mapperPackageNames.add(dataSourceConfigClass.getPackage().getName());
            SpringBootAppInitializer.packageNames.addAll(mapperPackageNames);
            return dataSourceConfigClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
