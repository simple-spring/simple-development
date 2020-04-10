package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableAlert;
import com.spring.simple.development.core.annotation.config.EnableShiroCas;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableShiroCas")
public class ShiroCasSpiConfig implements SimpleSpiConfig<EnableShiroCas> {
    @Override
    public Class getConfigClass(EnableShiroCas enableShiroCas) {
        try {
            // shiro参数校验
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SUCCESSURL));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_SKIPAUTHENTICATE));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_UNAUTHORIZEDURL));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_BIZSITEURLPREFIX));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_CASSERVERURLPREFIX));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_HOST));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_PORT));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_DATABASE));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_REDIS_PASSWORD));
            // 启动shiro
            boolean isEnableBoolean = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
            if (isEnableBoolean == false) {
                return null;
            }
            Class<?> targetClass = Class.forName("com.spring.simple.development.core.component.shiro.cas.ShiroCasConfig");
            return targetClass;
        } catch (Exception ex) {
            throw new RuntimeException("ShiroCasSpiConfig initialization failed", ex);
        }
    }
}
