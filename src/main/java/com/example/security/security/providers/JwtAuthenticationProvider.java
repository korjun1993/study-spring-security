package com.example.security.security.providers;

import com.example.security.JwtDecoder;
import com.example.security.security.AccountContext;
import com.example.security.security.tokens.JwtPreProcessingToken;
import com.example.security.security.tokens.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        AccountContext accountContext = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFromAccountContext(accountContext);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtPreProcessingToken.class.isAssignableFrom(aClass);
    }
}
