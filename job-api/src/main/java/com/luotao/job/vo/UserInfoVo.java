package com.luotao.job.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luotao
 * @description 用户信息响应VO
 */
@Data
public class UserInfoVo {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户状态(1:正常,0:禁用)
     */
    private Boolean status;
} 