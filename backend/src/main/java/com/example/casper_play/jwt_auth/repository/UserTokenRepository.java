package com.example.casper_play.jwt_auth.repository;

import com.example.casper_play.jwt_auth.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    void deleteByToken(String token);
    UserToken findByToken(String token);
    void deleteByUserId(Long userId);
} 