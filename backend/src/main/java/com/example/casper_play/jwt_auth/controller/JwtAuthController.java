package com.example.casper_play.jwt_auth.controller;

import com.example.casper_play.common.Result;
import com.example.casper_play.jwt_auth.JwtUtil;
import com.example.casper_play.jwt_auth.service.JwtAuthService;
import com.example.casper_play.jwt_auth.dto.LoginRequest;
import com.example.casper_play.jwt_auth.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {
    @Autowired
    private JwtAuthService jwtAuthService;

    // 登录接口
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest req) {
        String username = req.getUsername();
        String password = req.getPassword();
        String token = jwtAuthService.login(username, password);
        if (token == null) {
            return Result.error("用户名或密码错误");
        }
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    // 校验token接口
    @PostMapping("/verify")
    public Result<Map<String, Object>> verify(@RequestBody Map<String, String> req) {
        String token = req.get("token");
        boolean valid = JwtUtil.validateToken(token);
        Map<String, Object> data = new HashMap<>();
        data.put("valid", valid);
        return Result.success(data);
    }

    // 注册接口
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterRequest req) {
        boolean ok = jwtAuthService.register(req.getUsername(), req.getPassword());
        if (ok) {
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已存在");
        }
    }

    // 登出接口
    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        jwtAuthService.logout(token);
        return Result.success("登出成功");
    }
} 