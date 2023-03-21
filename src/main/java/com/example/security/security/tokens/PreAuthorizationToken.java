package com.example.security.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    /**
     * getPrinciple() 메서드명이 모호해서 새로만듦
     * 명시적으로 이름을 반환하는 메서드임을 나타내기
     */
    public String getUserName() {
        return (String) super.getPrincipal();
    }

    /**
     * getCredential() 메서드명이 모호해서 새로만듦
     * 명시적으로 암호를 반환하는 메서드임을 나타내기
     */
    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
