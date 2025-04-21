package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.User;
import com.luotao.job.service.UserService;
import com.luotao.job.mapper.UserMapper;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.luotao.job.utils.JwtUtil;
/**
* @author T
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-03-29 21:04:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseResult register(UserRegisterVo registerVo) {
        // 1. 校验两次密码是否一致
        if (!registerVo.getPassword().equals(registerVo.getConfirmPassword())) {
            return new ResponseResult(400, "两次密码不一致");
        }

        // 2. 校验用户名是否已存在：构建一个查询条件，要求 User 表中的 username 字段等于 registerVo.getUsername() 的值
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerVo.getUsername());
        if (this.count(queryWrapper) > 0) {
            return new ResponseResult(400, "用户名已存在");
        }

        // 3. 校验邮箱是否已存在
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, registerVo.getEmail());
        if (this.count(queryWrapper) > 0) {
            return new ResponseResult(400, "邮箱已被注册");
        }

        // 4. 创建用户实体
        User user = new User();
        BeanUtils.copyProperties(registerVo, user); //根据属性名匹配的方式，将 registerVo 中的非空属性值赋值给 user 对象的对应属性。
        // 设置默认昵称
        if (user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }
        // 密码加密
        /**
         调用 registerVo.getPassword() 获取用户输入的明文密码。
         使用 StandardCharsets.UTF_8 将密码字符串转换为字节数组。
         调用 DigestUtils.md5DigestAsHex 方法对密码字节数组进行 MD5 加密，并返回加密后的十六进制字符串。
         将加密后的密码通过 user.setPassword 设置到用户实体对象中。
         **/
        user.setPassword(DigestUtils.md5DigestAsHex(
                registerVo.getPassword().getBytes(StandardCharsets.UTF_8)));
        // 设置其他默认值
        user.setStatus(true);
        user.setRole("USER");
        user.setCreateTime(LocalDateTime.now());

        // 5. 保存用户。
        this.save(user);//save 方法会根据传入的实体对象 user，将其插入到对应的数据库表中

        return new ResponseResult(200, "注册成功");
    }

    @Override
    public ResponseResult login(UserLoginVo loginVo) {
        // 1. 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginVo.getUsername());
        User user = this.getOne(queryWrapper);//根据查询条件从数据库中查找一条记录。

        // 2. 校验用户是否存在
        if (user == null) {
            return new ResponseResult(400, "用户名或密码错误");
        }

        // 3. 校验密码。对用户登录时输入的密码进行加密后与数据库中存储的密码进行比对。
        String encryptedPassword = DigestUtils.md5DigestAsHex(
                loginVo.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(encryptedPassword)) {
            return new ResponseResult(400, "用户名或密码错误");
        }

        // 4. 校验用户状态
        if (!user.getStatus()) {
            return new ResponseResult(400, "账号已被禁用");
        }

        // 5. 更新登录时间
        user.setLastLogin(LocalDateTime.now());
        this.updateById(user);

        // 6. 生成JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        // 7. 返回用户信息和token
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);

        // 创建一个包含用户信息和token的响应对象
        LoginResponseVo loginResponseVo = new LoginResponseVo();
        loginResponseVo.setUserInfo(userInfoVo);
        loginResponseVo.setToken(token);

        return new ResponseResult(loginResponseVo);
    }

    @Override
    public ResponseResult<UserInfoVo> getUserInfo(Long userId) {
        // 1. 查询用户
        User user = this.getById(userId);//根据主键 ID 查询单个实体对象
        if (user == null) {
            return new ResponseResult(400, "用户不存在");
        }

        // 2. 转换为VO对象
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);

        return new ResponseResult(userInfoVo);
    }

    @Override
    public ResponseResult updatePassword(Long userId, UpdatePasswordVo updatePasswordVo) {
        // 1. 校验新密码与确认密码是否一致
        if (!updatePasswordVo.getNewPassword().equals(updatePasswordVo.getConfirmPassword())) {
            return new ResponseResult(400, "两次输入的新密码不一致");
        }

        // 2. 获取用户信息
        User user = this.getById(userId);
        if (user == null) {
            return new ResponseResult(400, "用户不存在");
        }

        // 3. 校验旧密码是否正确
        String oldEncryptedPassword = DigestUtils.md5DigestAsHex(
                updatePasswordVo.getOldPassword().getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(oldEncryptedPassword)) {
            return new ResponseResult(400, "旧密码错误");
        }

        // 4. 更新密码
        String newEncryptedPassword = DigestUtils.md5DigestAsHex(
                updatePasswordVo.getNewPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newEncryptedPassword);

        // 5. 保存更新
        this.updateById(user);

        return new ResponseResult(200, "密码修改成功");
    }


    @Override
    public ResponseResult<UserInfoVo> updateUserInfo(Long userId, UpdateUserInfoVo updateUserInfoVo) {
        // 1. 获取用户信息
        User user = this.getById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        // 2. 校验邮箱是否被其他用户使用（排除当前用户）
        if (updateUserInfoVo.getEmail() != null && !updateUserInfoVo.getEmail().equals(user.getEmail())) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, updateUserInfoVo.getEmail());
            if (this.count(queryWrapper) > 0) {
                return new ResponseResult<>(400, "邮箱已被其他用户使用");
            }
        }

        // 3. 更新用户信息
        boolean needUpdate = false;//使用needUpdate标记是否需要执行数据库更新

        if (updateUserInfoVo.getNickname() != null) {
            user.setNickname(updateUserInfoVo.getNickname());
            needUpdate = true;
        }

        if (updateUserInfoVo.getEmail() != null) {
            user.setEmail(updateUserInfoVo.getEmail());
            needUpdate = true;
        }

        if (updateUserInfoVo.getAvatar() != null) {
            user.setAvatar(updateUserInfoVo.getAvatar());
            needUpdate = true;
        }

        // 4. 保存更新
        if (needUpdate) {
            this.updateById(user);
        }

        // 5. 返回更新后的用户信息
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);

        return new ResponseResult<>(userInfoVo);
    }


    @Override
    public ResponseResult deactivateAccount(Long userId, String password) {
        // 1. 获取用户信息
        User user = this.getById(userId);
        if (user == null) {
            return new ResponseResult(400, "用户不存在");
        }

        // 2. 验证用户密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(
                password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(encryptedPassword)) {
            return new ResponseResult(400, "密码错误");
        }

        // 3. 检查是否是管理员账号
        if ("ADMIN".equals(user.getRole())) {
            return new ResponseResult(400, "管理员账号不能注销");
        }

        // 4. 软删除用户（设置状态为禁用）
        user.setStatus(false);
        this.updateById(user);

        return new ResponseResult(200, "账号已注销");
    }
}




