package com.luotao.oauth2.server.mapper;

import com.luotao.commons.model.entity.Users;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 mapper
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/2 15:31
 */
public interface UserMapper {
    /**
    * 根据用户名 or 手机号 or 邮箱这些 AccountInfo 查询用户信息
    *
    * @author: LuoTao
    * 2025-06-02 15:32:16
    **/
    Users selectByAccountInfo(@Param("account") String accountInfo);
}