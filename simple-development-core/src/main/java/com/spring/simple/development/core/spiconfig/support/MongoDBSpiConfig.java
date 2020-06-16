package com.spring.simple.development.core.spiconfig.support;

/**
 * @desc: MongoDB
 * @auth: hjs
 */
import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableMongoDB;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;


@Spi(configName = "EnableMongoDB")
public class MongoDBSpiConfig implements SimpleSpiConfig<EnableMongoDB> {
    @Override
    public Class getConfigClass(EnableMongoDB enableMongoDB) {
        try {
            Class<?> aClass = Class.forName("com.spring.simple.development.core.component.mongodb.config.MongoDBConfig");
            return aClass;
        } catch (Exception ex) {
            throw new RuntimeException("MongoDBConfig initialization failed", ex);
        }
    }
}
