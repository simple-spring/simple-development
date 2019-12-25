package com.chexin.simple.development.core.config.support;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.EnableRedis;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.redis.RedisConfig;

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
