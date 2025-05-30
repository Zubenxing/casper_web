package com.example.casper_play.web_passwd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.casper_play.web_passwd.entity.WebPasswd;

@Repository
public interface WebPasswdRepository extends JpaRepository<WebPasswd, Long> {
    // TODO: 可自定义查询方法
} 