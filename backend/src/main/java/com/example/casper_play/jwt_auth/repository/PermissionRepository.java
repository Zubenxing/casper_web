package com.example.casper_play.jwt_auth.repository;

import com.example.casper_play.jwt_auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
} 