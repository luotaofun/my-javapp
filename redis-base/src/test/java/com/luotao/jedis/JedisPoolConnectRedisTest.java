package com.luotao.jedis;

import com.luotao.utils.JedisPoolConnectRedis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * 连接redis
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/5/27 20:58
 */
public class JedisPoolConnectRedisTest {
    Jedis jedis = null;
    @BeforeEach
    public void initTest(){
//        jedis = new Jedis("172.22.250.63", 6379); // 不使用连接池
        // 从连接池中获取 Jedis 连接对象
        jedis = JedisPoolConnectRedis.getJedis();
        jedis.auth("kuroneko.678");
        System.out.println("心跳机制检测是否连接成功：" +jedis.ping());
    }
    @Test
    public void stringTest(){
        // 选择库
        jedis.select(0);
        // 插入数据
        System.out.println(jedis.set("test:users:1", "neko"));
        // 获取数据
        System.out.println(jedis.get("test:users:1"));
        // 获取所有的键
        System.out.println(jedis.keys("*"));

    }
    @AfterEach
    public void close(){
        if (jedis != null){
            jedis.close();
            System.out.println("redis连接已关闭");
        }
    }


}