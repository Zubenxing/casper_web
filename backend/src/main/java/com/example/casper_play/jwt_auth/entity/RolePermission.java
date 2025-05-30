package com.example.casper_play.jwt_auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_permission")
@IdClass(RolePermission.PK.class)
public class RolePermission {
    @Id
    private Long roleId;
    @Id
    private Long permissionId;

    @Data
    public static class PK implements Serializable {
        private Long roleId;
        private Long permissionId;
    }
} 