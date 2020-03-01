package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableDubbo;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableDubbo")
public class DubboSpiConfig implements SimpleSpiConfig<EnableDubbo> {
    @Override
    public Class getConfigClass(EnableDubbo enableDubbo) {
        try {
            // dubbo参数校验
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_APPLICATION_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT));
            // 添加DataSourceConfig MapperScan扫描包的路径
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(enableDubbo.dubboPackage())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE, basePackageName + PackageNameConstant.DUBBO);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE, enableDubbo.dubboPackage());
            }
            List<String> mapperPackageNames = new ArrayList<>();
            mapperPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE));
            Class<?> dubboClass = Class.forName("com.spring.simple.development.core.component.dubbo.DubboConfig");
            Class dubboConfig = ClassLoadUtil.javassistCompile(dubboClass, "com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan", mapperPackageNames, "basePackages");
            return dubboConfig;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
