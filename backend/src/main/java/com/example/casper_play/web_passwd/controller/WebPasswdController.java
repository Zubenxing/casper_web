package com.example.casper_play.web_passwd.controller;

import com.example.casper_play.common.Result;
import com.example.casper_play.web_passwd.entity.WebPasswd;
import com.example.casper_play.web_passwd.service.WebPasswdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.casper_play.jwt_auth.JwtUtil;
import com.example.casper_play.jwt_auth.repository.UserRepository;
import com.example.casper_play.jwt_auth.repository.RoleRepository;


import java.util.List;

@Tag(name = "账号密码管理")
@RestController
@RequestMapping("/api/web_passwd")
public class WebPasswdController {
    @Autowired
    private WebPasswdService webPasswdService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // 删除所有未带userId/roleName参数的add和list方法实现，只保留新版。

    // 删除账号密码
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        webPasswdService.delete(id);
        return Result.success(null);
    }

    // 新增账号密码
    @PostMapping
    public Result<WebPasswd> add(@RequestBody WebPasswd webPasswd, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.getUsername(token);
        var user = userRepository.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        Long userId = user.getId();
        return Result.success(webPasswdService.add(webPasswd, userId));
    }

    // 分页查询账号密码列表
    @GetMapping("/list")
    public Result<Page<WebPasswd>> list(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.getUsername(token);
        var user = userRepository.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        Long userId = user.getId();
        String roleName = roleRepository.findById(user.getRoleId())
            .map(r -> r.getName()).orElse("user");
        return Result.success(webPasswdService.list(page, size, userId, roleName));
    }
} 