package com.chexin.simple.development.core.redis;

import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.chexin.simple.development.support.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {
    public RedisConfig() {
        System.out.println("redis initialized...");
        init(PropertyConfigurer.getProperty("redisHost"), PropertyConfigurer.getProperty("redisPort"),
                PropertyConfigurer.getProperty("redisPwd"),
                Long.valueOf(PropertyConfigurer.getProperty("maxTotal")).intValue(),
                Long.valueOf(PropertyConfigurer.getProperty("maxWaitMillis")),
                Long.valueOf(PropertyConfigurer.getProperty("maxIdle")).intValue(),
                Long.valueOf(PropertyConfigurer.getProperty("timeout")).intValue());
        System.out.println("redis initialized successfully");
    }

    /**
     * 初始化Redis连接池
     */
    public synchronized static void init(String host, String port, String pwd, int maxTotal, long maxWaitMillis, int maxIdle, int timeout) {

        JedisPool pool = JedisPoolUtils.getPool();
        if (null != pool) {
            return;
        }
        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数 7000
        config.setMaxTotal(maxTotal);
        // 设置最大阻塞时间，3000毫秒
        config.setMaxWaitMillis(maxWaitMillis);
        // 设置空间连接 10
        config.setMaxIdle(maxIdle);
        pool = new JedisPool(config, host, Long.valueOf(port).intValue(), timeout, pwd);
        // 测试是否成功
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            jedis.ping();
        } catch (Exception e) {
            System.out.println("redis Connection failed");
            throw new RuntimeException(e);
        } finally {
            JedisPoolUtils.returnRes(jedis);
        }
        System.out.println("redis Connection successful");
    }
}
