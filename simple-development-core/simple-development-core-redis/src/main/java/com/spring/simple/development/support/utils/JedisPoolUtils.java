package com.spring.simple.development.support.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * desc:    Redis连接池
 */

public class JedisPoolUtils {

    private static JedisPool pool;

    public static JedisPool getPool() {
        return pool;
    }

    public static void setPool(JedisPool pool) {
        JedisPoolUtils.pool = pool;
    }

    /**
     * 初始化Redis连接池
     */
    public synchronized static void init(String host, String port, String pwd,int maxTotal,long maxWaitMillis,int maxIdle,int timeout) {

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
        pool = new JedisPool(config,host,Long.valueOf(port).intValue(),timeout,pwd);
        // 测试是否成功
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            jedis.ping();
        } catch (Exception e) {
            System.out.println("redis 连接失败");
            throw new RuntimeException(e);
        } finally {
            JedisPoolUtils.returnRes(jedis);
        }
        System.out.println("redis 连接成功");
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        if (null != jedis) {
            pool.returnResource(jedis);
        }
    }
}
