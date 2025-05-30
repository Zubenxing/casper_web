package com.example.casper_play.jwt_auth.service;

import com.example.casper_play.jwt_auth.entity.User;
import com.example.casper_play.jwt_auth.repository.UserRepository;
import com.example.casper_play.jwt_auth.JwtUtil;
import com.example.casper_play.common.AesUtil;
import com.example.casper_play.jwt_auth.entity.Role;
import com.example.casper_play.jwt_auth.entity.UserToken;
import com.example.casper_play.jwt_auth.repository.UserTokenRepository;
import com.example.casper_play.jwt_auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class JwtAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    // 登录校验，返回token，失败返回null
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        // 兼容明文和加密密码
        String dbPwd = user.getPassword();
        boolean match = password.equals(dbPwd) || password.equals(AesUtil.decrypt(dbPwd));
        if (!match) return null;
        // 更新last_login_time
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
        String token = JwtUtil.generateToken(username);
        // 写入user_token表
        UserToken userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setExpireTime(LocalDateTime.now().plusDays(7)); // 例如7天有效期
        userTokenRepository.save(userToken);
        return token;
    }

    // 登出
    public void logout(String token) {
        userTokenRepository.deleteByToken(token);
    }

    // 注册普通用户
    public boolean register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(AesUtil.encrypt(password));
        // 查找user角色
        Role userRole = roleRepository.findByName("user");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("user");
            userRole.setDescription("普通用户");
            userRole = roleRepository.save(userRole);
        }
        user.setRoleId(userRole.getId());
        userRepository.save(user);
        return true;
    }
} 