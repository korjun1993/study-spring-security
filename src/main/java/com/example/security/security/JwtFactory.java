package com.example.security.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JwtFactory {
    private static String signingKey = "jwt";

    public String generateToken(AccountContext context) {
        String token = null;

        List<GrantedAuthority> authorities = List.copyOf(context.getAuthorities());

        try {
            token = JWT.create()
                    .withIssuer("spring-security")
                    .withClaim("USERNAME", context.getUsername())
                    .withClaim("USER_ROLE", authorities.get(0).getAuthority())
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }
}
