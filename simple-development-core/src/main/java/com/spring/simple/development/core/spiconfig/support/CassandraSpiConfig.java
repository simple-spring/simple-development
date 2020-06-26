package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableCassandra;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
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
@Spi(configName = "EnableCassandra")
public class CassandraSpiConfig implements SimpleSpiConfig<EnableCassandra> {
    @Override
    public Class getConfigClass(EnableCassandra enableCassandra) {
        try {
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_ADDRESS));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_KEY_SPACES_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_USERNAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_PASSWORD));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_PORT));
            // 修改CassandraSpiConfig EnableCassandraRepositories扫描包的路径

            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(enableCassandra.cassandraPackage())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE, basePackageName + PackageNameConstant.CASSANDRA);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE, enableCassandra.cassandraPackage());
            }
            List<String> cassandraPackageNames = new ArrayList<>();
            cassandraPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_PACKAGE));
            Class<?> targetClass = Class.forName("com.spring.simple.development.core.component.cassandra.CassandraConfig");
            Class rootConfig = ClassLoadUtil.javassistCompile(targetClass, "org.springframework.data.cassandra.repository.config.EnableCassandraRepositories", cassandraPackageNames, "basePackages");
            cassandraPackageNames.add(targetClass.getPackage().getName());
            SpringBootAppInitializer.packageNames.addAll(cassandraPackageNames);
            return rootConfig;
        } catch (Exception ex) {
            throw new RuntimeException("CassandraConfig initialization failed", ex);
        }
    }
}
