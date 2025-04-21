package com.luotao.job.vo;

import lombok.Data;

/**
 * @Classname LoginResponseVo
 * @Description 用于封装用户信息和token
 * @Version 1.0.0
 * @Date 2025/4/2 19:37
 * @Author LuoTao
 */
@Data
public class LoginResponseVo {
    private UserInfoVo userInfo;
    private String token;
}