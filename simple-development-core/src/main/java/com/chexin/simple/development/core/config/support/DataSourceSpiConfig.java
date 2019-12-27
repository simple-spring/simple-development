package com.chexin.simple.development.core.config.support;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.EnableMybatis;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.constant.PackageNameConstant;
import com.chexin.simple.development.core.constant.SystemProperties;
import com.chexin.simple.development.core.jdbc.DataSourceConfig;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.chexin.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableMybatis")
public class DataSourceSpiConfig implements SimpleSpiConfig<EnableMybatis, DataSourceConfig> {
    @Override
    public Class<DataSourceConfig> getConfigClass(EnableMybatis enableMybatis) {
        try {
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME);
            // mapper xml路径
            String defaultMapperXmlPath = "classpath:mybatis/*/*.xml";
            if (StringUtils.isEmpty(enableMybatis.mapperXmlPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPERXMLPATH, defaultMapperXmlPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPERXMLPATH, enableMybatis.mapperXmlPath());
            }

            // model 路径
            String defaultModelPath = basePackageName + PackageNameConstant.MODEL;
            if (StringUtils.isEmpty(enableMybatis.modelPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODELPATH, defaultModelPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODELPATH, enableMybatis.modelPath());
            }
            // 事务表达式
            String defaultExpression = "execution (* " + basePackageName + ".service..*.*(..))";
            if (StringUtils.isEmpty(enableMybatis.expression())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_EXPRESSION, defaultExpression);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_EXPRESSION, enableMybatis.expression());
            }
            // mapper 路径
            String defaultMapperPath = basePackageName + PackageNameConstant.MAPPER;
            if (StringUtils.isEmpty(enableMybatis.mapperPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPERPATH, defaultMapperPath);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPERPATH, enableMybatis.mapperPath());
            }
            // 添加DataSourceConfig MapperScan扫描包的路径
            List<String> mapperPackageNames = new ArrayList<>();
            mapperPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPERPATH));
            Class dataSourceConfigClass = ClassLoadUtil.javassistCompile(DataSourceConfig.class, "org.mybatis.spring.annotation.MapperScan", mapperPackageNames, "basePackages");
            return dataSourceConfigClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
