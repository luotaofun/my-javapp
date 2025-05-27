package com.luotao.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池工具类
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/5/27 21:27
 */
public class JedisPoolConnectRedis {
    private static JedisPool jedisPool;
    static {
        // 创建连接池对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大连接数,默认8
        jedisPoolConfig.setMaxTotal(8);
        // 设置最大空闲连接数,默认8
        jedisPoolConfig.setMaxIdle(8);
        // 设置最小空闲连接数,默认0
        jedisPoolConfig.setMinIdle(0);
        // 设置等待时间,默认-1
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPool = new JedisPool(jedisPoolConfig,"172.22.250.63",6379,100,"kuroneko.678");
    }
    /**
     * 获取连接池中的Jedis连接对象
     * @return Jedis连接对象
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}