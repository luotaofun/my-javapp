package com.luotao.job.service;

import com.luotao.job.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.*;

/**
* @author T
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-03-29 21:04:08
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param registerVo 注册信息
     * @return 注册结果
     */
    ResponseResult register(UserRegisterVo registerVo);

    /**
     * 用户登录
     *
     * @param loginVo 登录信息
     * @return 登录结果
     */
    ResponseResult login(UserLoginVo loginVo);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    ResponseResult<UserInfoVo> getUserInfo(Long userId);


    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param updatePasswordVo 修改密码信息
     * @return 修改结果
     */
    ResponseResult updatePassword(Long userId, UpdatePasswordVo updatePasswordVo);


    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param updateUserInfoVo 更新的用户信息
     * @return 更新结果
     */
    ResponseResult<UserInfoVo> updateUserInfo(Long userId, UpdateUserInfoVo updateUserInfoVo);


    /**
     * 注销用户
     *
     * @param userId 用户ID
     * @param password 用户密码（验证身份）
     * @return 注销结果
     */
    ResponseResult deactivateAccount(Long userId, String password);
}
