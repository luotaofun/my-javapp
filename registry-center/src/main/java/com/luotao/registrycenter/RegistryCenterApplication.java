package com.luotao.registrycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * TODO
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/5/28 17:46
 */
@EnableEurekaServer // 开启服务注册中心
@SpringBootApplication
public class RegistryCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistryCenterApplication.class, args);
    }

}