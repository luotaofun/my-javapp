package com.luotao.job;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest( classes = {JobApplication.class})
class JobApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @BeforeEach
    void initTest() {
        System.out.println("心跳机制检测是否连接成功：" + redisTemplate.getConnectionFactory().getConnection().ping());
    }

    @Test
    public void stringTest(){
        redisTemplate.opsForValue().set("test:users:1", "neko");// 插入数据
        System.out.println("获取值" + redisTemplate.opsForValue().get("test:users:1"));
        System.out.println("获取所有的键" + redisTemplate.keys("*"));

    }
    @AfterEach
    public void close(){
        if (redisTemplate!= null){
            redisTemplate.getConnectionFactory().getConnection().close();
            System.out.println("redis连接已关闭");
        }
    }

}
