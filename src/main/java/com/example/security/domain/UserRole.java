package com.example.security.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    private UserRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static UserRole getRoleByName(String roleName) {
        return Arrays.stream(UserRole.values()).filter(r -> r.isCorrectName(roleName)).findFirst().orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
    }
}
