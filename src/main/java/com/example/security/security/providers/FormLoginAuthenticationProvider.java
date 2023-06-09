package com.example.security.security.providers;

import com.example.security.domain.Account;
import com.example.security.domain.AccountRepository;
import com.example.security.security.AccountContext;
import com.example.security.security.AccountContextService;
import com.example.security.security.tokens.PostAuthorizationToken;
import com.example.security.security.tokens.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final AccountContextService accountContextService;

    /**
     * @param authentication 인증처리를 하기 전 인증객체
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUserName();
        String password = token.getUserPassword();

        AccountContext account = (AccountContext) accountContextService.loadUserByUsername(username);

        if (isCorrectPassword(password, account.getPassword())) {
            return PostAuthorizationToken.getTokenFromAccountContext(account);
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    /**
     * @param authentication
     * @return AuthenticationManager에 의해 호출. 해당 Provider가 인증을 하기에 적절하면 true, 아니면 false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String requestPassword, String password) {
        return passwordEncoder.matches(password, requestPassword);
    }
}
