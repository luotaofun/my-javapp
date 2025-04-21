package com.luotao.job.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MybatisPlusConfig
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/3/26 17:32
 * @Author LuoTao
 */
@Configuration
@MapperScan("com.luotao.job.mapper")
public class MybatisPlusConfig {

    /**
    * @Description: 添加分页插件。
     * 创建MybatisPlusInterceptor实例。
     * 添加PaginationInnerInterceptor作为内部拦截器，并指定数据库类型为MariaDB。
     * 返回配置好的拦截器实例。
    **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor((DbType.MARIADB)));
        return interceptor;
    }
}

