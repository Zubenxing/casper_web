package com.example.casper_play.jwt_auth.init;

import com.example.casper_play.jwt_auth.entity.Role;
import com.example.casper_play.jwt_auth.entity.User;
import com.example.casper_play.jwt_auth.repository.RoleRepository;
import com.example.casper_play.jwt_auth.repository.UserRepository;
import com.example.casper_play.common.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SuperAdminInitializer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${superadmin.username:superadmin}")
    private String superadminUsername;

    @Value("${superadmin.password:superadmin123}")
    private String superadminPassword;

    @PostConstruct
    public void initSuperAdmin() {
        if (userRepository.findByUsername(superadminUsername) == null) {
            // 创建superadmin用户
            User user = new User();
            user.setUsername(superadminUsername);
            user.setPassword(AesUtil.encrypt(superadminPassword));
            // 创建superadmin角色（如不存在）
            Role superRole = roleRepository.findByName("superadmin");
            if (superRole == null) {
                superRole = new Role();
                superRole.setName("superadmin");
                superRole.setDescription("超级管理员");
                superRole = roleRepository.save(superRole);
            }
            user.setRoleId(superRole.getId());
            userRepository.save(user);
            System.out.println("已自动创建超级管理员账号，用户名: " + superadminUsername + "，密码: " + superadminPassword);
        }
    }
} 