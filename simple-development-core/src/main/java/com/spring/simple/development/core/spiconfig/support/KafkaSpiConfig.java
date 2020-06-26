package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableKafka;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @auth: hjs
 */
@Spi(configName = "EnableKafka")
public class KafkaSpiConfig implements SimpleSpiConfig<EnableKafka> {

    @Override
    public Class getConfigClass(EnableKafka enableKafka) {
        try {
            // kafka参数校验
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_KAFKA_BOOTSTRAP_SERVERS));
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(enableKafka.packagePath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_KAFKA_CONFIG_PACKAGE, basePackageName + PackageNameConstant.KAFKA);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_KAFKA_CONFIG_PACKAGE, enableKafka.packagePath());
            }
            Class<?> aClass = Class.forName("com.spring.simple.development.core.component.kafka.config.KafkaConfig");
            SpringBootAppInitializer.packageNames.add(aClass.getPackage().getName());
            return aClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }

}
