package com.luotao.oauth2.server.service;

import com.luotao.commons.model.entity.Users;
import com.luotao.commons.utils.AssertUtil;
import com.luotao.oauth2.server.mapper.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * 登录校验
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/2 15:28
 */
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AssertUtil.isNotEmpty(username, "用户名不能为空");
        Users users = userMapper.selectByAccountInfo(username);
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(username, users.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRoles())) ;
    }
}