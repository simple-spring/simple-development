package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableRedis;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.core.component.redis.RedisConfig;

/**
 * @author liko wang
 */
@Spi(configName = "EnableRedis")
public class RedisSpiConfig implements SimpleSpiConfig<EnableRedis, RedisConfig> {
    @Override
    public Class<RedisConfig> getConfigClass(EnableRedis enableRedis) {
        try {
            return RedisConfig.class;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
