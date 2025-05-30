package com.example.casper_play.web_passwd.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "web_passwd")
@Data
public class WebPasswd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username; // 账号

    @Column
    private String email; // 邮箱，可为空

    @Column(nullable = false)
    private String password; // 密码

    @Column(nullable = false)
    private String siteName; // 网站名称

    private String remark; // 备注，可为空

    @Column(nullable = false)
    private Integer webType; // 网站类型 1=工作 2=个人 3=游戏 4=其他

    @Column(nullable = false)
    private Long userId; // 所属用户ID
} 