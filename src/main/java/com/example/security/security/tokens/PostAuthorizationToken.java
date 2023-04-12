package com.example.security.security.tokens;

import com.example.security.security.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static AccountContext fromJwtPost(JwtPostProcessingToken token) {
        return new AccountContext(token.getUserId(), token.getPassword(), token.getAuthorities());
    }

    public static PostAuthorizationToken getTokenFromAccountContext(AccountContext context) {
        return new PostAuthorizationToken(context, context.getPassword(), context.getAuthorities());
    }
}
