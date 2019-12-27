package com.chexin.simple.development.core.config.support;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.EnableDubbo;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.constant.PackageNameConstant;
import com.chexin.simple.development.core.constant.SystemProperties;
import com.chexin.simple.development.core.dubbo.DubboConfig;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.chexin.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableDubbo")
public class DubboSpiConfig implements SimpleSpiConfig<EnableDubbo, DubboConfig> {
    @Override
    public Class<DubboConfig> getConfigClass(EnableDubbo enableDubbo) {
        try {
            // dubbo参数校验
            Assert.isNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_APPLICATION_NAME));
            Assert.isNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS));
            Assert.isNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME));
            Assert.isNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT));
            // 添加DataSourceConfig MapperScan扫描包的路径
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME);
            if (StringUtils.isEmpty(enableDubbo.dubboPackage())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBOPACKAGE, basePackageName + PackageNameConstant.DUBBO);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBOPACKAGE, enableDubbo.dubboPackage());
            }
            List<String> mapperPackageNames = new ArrayList<>();
            mapperPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBOPACKAGE));
            Class dubboConfig = ClassLoadUtil.javassistCompile(DubboConfig.class, "com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan", mapperPackageNames, "basePackages");
            return dubboConfig;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
