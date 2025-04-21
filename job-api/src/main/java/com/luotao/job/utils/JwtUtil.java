package com.luotao.job.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，提供生成和解析JWT（JSON Web Token）的功能
 * 在用户登录成功后，生成一个JWT并返回给客户端。客户端在后续请求中携带这个JWT，服务器通过解析JWT来验证用户的身份。
 * 服务器在接收到客户端请求时，解析JWT以验证用户的身份和权限。
 * 提取信息：从JWT中提取用户信息（如用户ID、角色等），用于授权和业务逻辑处理。
 */
@Component
public class JwtUtil {

    // 从配置文件中注入JWT密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从配置文件中注入JWT过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT token
     * 在用户登录成功后调用此方法生成JWT，JWT中包含用户信息（用户ID、用户名、角色），并设置过期时间。
     *
     * @param userId 用户ID
     * @param username 用户名
     * @param role 用户角色
     * @return 生成的JWT token
     */
    public String generateToken(Long userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析JWT token
     * 服务器接收到客户端请求时，通过此方法解析JWT，以验证用户的身份和权限。
     *
     * @param token 客户端携带的JWT token
     * @return 解析后的JWT claims，包含用户信息和过期时间等
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取JWT签名密钥
     * 使用HS256算法生成密钥，确保JWT的安全性。
     *
     * @return 生成的密钥
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
