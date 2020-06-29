package com.spring.simple.development.core.component.redis;

import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.JedisPoolUtils;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis组件
 *
 * @author liko wang
 */
//@EnableCaching
@Configuration
public class RedisConfig {
    private String host = PropertyConfigurer.getProperty("spring.simple.redisHost");
    private String port = PropertyConfigurer.getProperty("spring.simple.redisPort");
    private String pwd = PropertyConfigurer.getProperty("spring.simple.redisPwd");
    private Long maxTotal = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.maxTotal"));
    private Long maxWaitMillis = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.maxWaitMillis"));
    private Long maxIdle = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.maxIdle"));
    private Long timeout = Long.valueOf(PropertyConfigurer.getProperty("spring.simple.timeout"));

    public RedisConfig() {
        System.out.println("redis initialized...");
        Assert.notNull(host, "redis host is empty");
        Assert.notNull(port, "redis post is empty");
        Assert.notNull(pwd, "redis pwd is empty");
        Assert.notNull(maxTotal, "redis maxTotal is empty");
        Assert.notNull(maxWaitMillis, "redis maxWaitMillis is empty");
        Assert.notNull(maxIdle, "redis maxIdle is empty");
        Assert.notNull(timeout, "redis timeout is empty");
        init(host, port, pwd, maxTotal, maxWaitMillis, maxIdle, timeout);
        System.out.println("redis initialized successfully");
    }

    /**
     * 初始化Redis连接池
     */
    public synchronized static void init(String host, String port, String pwd, Long maxTotal, Long maxWaitMillis, Long maxIdle, Long timeout) {

        JedisPool pool = JedisPoolUtils.getPool();
        if (null != pool) {
            return;
        }
        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数 7000
        config.setMaxTotal(maxTotal.intValue());
        // 设置最大阻塞时间，3000毫秒
        config.setMaxWaitMillis(maxWaitMillis);
        // 设置空间连接 10
        config.setMaxIdle(maxIdle.intValue());
        pool = new JedisPool(config, host, Long.valueOf(port).intValue(), timeout.intValue(), pwd);
        JedisPoolUtils.setPool(pool);
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
//
//    @Bean(value = "simpleRedisConnectionFactory")
//    public JedisConnectionFactory getJedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setUsePool(true);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPort(Long.valueOf(port).intValue());
//        jedisConnectionFactory.setDatabase(0);
//        jedisConnectionFactory.setPassword(pwd);
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }
//
//    @Bean(value = "cacheRedisTemplate")
//    public RedisTemplate<String, String> getRedisTemplate(RedisConnectionFactory simpleRedisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(simpleRedisConnectionFactory);
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        return redisTemplate;
//    }
//
//    @Bean
//    public RedisCacheManager getRedisCacheManager(RedisTemplate cacheRedisTemplate) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(cacheRedisTemplate);
//        redisCacheManager.setUsePrefix(true);
//        redisCacheManager.setDefaultExpiration(3600);
//        redisCacheManager.afterPropertiesSet();
//        return redisCacheManager;
//    }

}
