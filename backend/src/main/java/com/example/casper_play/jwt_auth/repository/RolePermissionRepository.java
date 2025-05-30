package com.example.casper_play.jwt_auth.repository;

import com.example.casper_play.jwt_auth.entity.RolePermission;
import com.example.casper_play.jwt_auth.entity.RolePermission.PK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, PK> {
    List<RolePermission> findByRoleId(Long roleId);
} 