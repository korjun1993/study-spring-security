package com.example.security.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    private UserRole(String roleName) {
        this.roleName = roleName;
    }
}
