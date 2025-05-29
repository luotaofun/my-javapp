package com.luotao.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户微服务
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/5/28 19:42
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}