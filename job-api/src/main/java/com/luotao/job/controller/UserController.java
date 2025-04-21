package com.luotao.job.controller;

import com.luotao.job.service.UserService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.UserLoginVo;
import com.luotao.job.vo.UserRegisterVo;
import com.luotao.job.vo.UserInfoVo;
import com.luotao.job.vo.UpdatePasswordVo;
import com.luotao.job.vo.UpdateUserInfoVo;
import com.luotao.job.vo.DeactivateAccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author luotao
 * @description 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param registerVo 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseResult register(@Validated @RequestBody UserRegisterVo registerVo) {
        log.info("用户注册，注册信息：{}", registerVo);
        return userService.register(registerVo);
    }

    /**
     * 用户登录
     *
     * @param loginVo 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@Validated @RequestBody UserLoginVo loginVo) {
        log.info("用户登录，登录信息：{}", loginVo);
        return userService.login(loginVo);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    @ApiOperation("获取用户信息")
    public ResponseResult<UserInfoVo> getUserInfo(@PathVariable Long userId) {
        log.info("获取用户信息，用户ID：{}", userId);
        return userService.getUserInfo(userId);
    }

    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param updatePasswordVo 修改密码信息
     * @return 修改结果
     */
    @PutMapping("/{userId}/password")
    @ApiOperation("修改密码")
    public ResponseResult updatePassword(
            @PathVariable Long userId,
            @Validated @RequestBody UpdatePasswordVo updatePasswordVo) {
        log.info("修改密码，用户ID：{}，修改密码信息：{}", userId, updatePasswordVo);
        return userService.updatePassword(userId, updatePasswordVo);
    }

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param updateUserInfoVo 更新的用户信息
     * @return 更新结果
     */
    @PutMapping("/{userId}")
    @ApiOperation("更新用户信息")
    public ResponseResult<UserInfoVo> updateUserInfo(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateUserInfoVo updateUserInfoVo) {
        log.info("更新用户信息，用户ID：{}，更新信息：{}", userId, updateUserInfoVo);
        return userService.updateUserInfo(userId, updateUserInfoVo);
    }

    /**
     * 注销账号
     *
     * @param userId 用户ID
     * @param deactivateAccountVo 注销信息（包含密码验证）
     * @return 注销结果
     */
    @DeleteMapping("/{userId}")
    @ApiOperation("注销账号")
    public ResponseResult deactivateAccount(
            @PathVariable Long userId,
            @Validated @RequestBody DeactivateAccountVo deactivateAccountVo) {
        log.info("注销账号，用户ID：{}", userId);
        return userService.deactivateAccount(userId, deactivateAccountVo.getPassword());
    }
} 