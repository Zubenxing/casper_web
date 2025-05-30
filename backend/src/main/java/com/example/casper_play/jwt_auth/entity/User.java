package com.example.casper_play.jwt_auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "last_login_time")
    private java.time.LocalDateTime lastLoginTime;
} 