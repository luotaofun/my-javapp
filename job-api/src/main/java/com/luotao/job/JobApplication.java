package com.luotao.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
//@MapperScan("com.luotao.job.mapper")//扫描接口所在包 mybatisplus会自动扫描不用配置
@EnableOpenApi//激活 API 文档功能
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

}
