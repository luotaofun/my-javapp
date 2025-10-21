package com.luotao.oauth2.server.config;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Security 配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // 注入 Redis 连接工厂
/*    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    // 初始化 RedisTokenStore 用于将 token 存储至 Redis
     @Bean // Temporarily commented out as new Authorization Server uses OAuth2AuthorizationService
     public RedisTokenStore redisTokenStore() {
         RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
         redisTokenStore.setPrefix("TOKEN:"); // 设置key的层级前缀，方便查询
         return redisTokenStore;
     }*/

    // 初始化密码编码器，用 MD5 加密密码 (注意：MD5 不安全，生产环境建议使用 BCryptPasswordEncoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            /**
             * 加密
             * @param rawPassword 原始密码
             * @return
             */
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtil.md5Hex(rawPassword.toString());
            }

            /**
             * 校验密码
             * @param rawPassword       原始密码
             * @param encodedPassword   加密密码
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return DigestUtil.md5Hex(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    // 初始化认证管理对象
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 放行和认证规则
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 放行的请求
                .antMatchers("/oauth/**", "/actuator/**").permitAll()
                .and()
                .authorizeRequests()
                // 其他请求必须认证才能访问
                .anyRequest().authenticated();
        return http.build();
    }

}
