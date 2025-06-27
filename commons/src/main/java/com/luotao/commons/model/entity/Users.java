package com.luotao.commons.model.entity;

import com.luotao.commons.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/2 16:04
 */
@Getter
@Setter
public class Users extends BaseModel {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String email;
    private Integer avatarUrl;
    private String roles;
}