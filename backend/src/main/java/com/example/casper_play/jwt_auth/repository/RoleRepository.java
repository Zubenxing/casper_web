package com.example.casper_play.jwt_auth.repository;

import com.example.casper_play.jwt_auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
} 