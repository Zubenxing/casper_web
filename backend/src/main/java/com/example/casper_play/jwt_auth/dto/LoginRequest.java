package com.example.casper_play.jwt_auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
} 