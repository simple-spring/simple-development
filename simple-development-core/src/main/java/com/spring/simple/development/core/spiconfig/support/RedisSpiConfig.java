package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableRedis;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;

/**
 * @author liko wang
 */
@Spi(configName = "EnableRedis")
public class RedisSpiConfig implements SimpleSpiConfig<EnableRedis> {
    @Override
    public Class getConfigClass(EnableRedis enableRedis) {
        try {
            Class<?> aClass = Class.forName("component.redis.RedisConfig");
            return aClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
