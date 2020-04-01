package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableAlert;
import com.spring.simple.development.core.annotation.config.EnableCassandra;
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
 * @author Administrator
 */
@Spi(configName = "EnableAlert")
public class AlertSpiConfig implements SimpleSpiConfig<EnableAlert> {
    @Override
    public Class getConfigClass(EnableAlert enableAlert) {
        try {
            boolean isOpen = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_IS_OPEN));
            if (!isOpen) {
                return null;
            }

            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_COLLECTION_URL));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_CODE));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_TOKEN));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_LOG_PATH));

            List<String> cassandraPackageNames = new ArrayList<>();
            cassandraPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE));
            Class<?> targetClass = Class.forName("com.spring.simple.development.core.component.alertsdk.handler.SimpleAlertConfig");
            return targetClass;
        } catch (Exception ex) {
            throw new RuntimeException("AlertSpiConfig initialization failed", ex);
        }
    }
}
