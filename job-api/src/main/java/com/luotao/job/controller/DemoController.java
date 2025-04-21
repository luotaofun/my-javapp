package com.luotao.job.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 处理客户端的HTTP请求，协调业务处理，根据请求返回数据
 */
@RestController // @RestController 是 @ResponseBody、@Controller 的组合注解
@ConfigurationProperties(prefix = "spring.datasource")//读取配置文件的属性注解,属性名需一致并通过setter注入
public class DemoController {
    private String username;
    private String password;
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password = password;
    }


    /**
     * @Description Value注解可以读取配置文件
     * http://127.0.0.1:8080/get/yaml
     **/
    @GetMapping("/get/yaml")
    public void getPropertiesValue(@Value("${server.port}") String port){
        System.out.println("================");
        System.out.println("port is " + port);
        System.out.println(username + ":" + password);
        System.out.println("================");
    }
}
