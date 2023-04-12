package com.example.security.security.filters;

import com.example.security.HeaderTokenExtractor;
import com.example.security.security.handlers.JwtAuthenticationFailureHandler;
import com.example.security.security.tokens.JwtPreProcessingToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtAuthenticationFailureHandler failureHandler;
    private final HeaderTokenExtractor extractor;

    public JwtAuthenticationFilter(RequestMatcher matcher, JwtAuthenticationFailureHandler failureHandler, HeaderTokenExtractor extractor) {
        super(matcher);
        super.setAuthenticationFailureHandler(failureHandler);
        this.failureHandler = failureHandler;
        this.extractor = extractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String payload = request.getHeader("Authorization");

        JwtPreProcessingToken jwtPreProcessingToken = new JwtPreProcessingToken(this.extractor.extract(payload));

        return super.getAuthenticationManager().authenticate(jwtPreProcessingToken);
    }

    // attemptAuthentication -> 성공적으로 호출됐을 경우, 호출되는 로직
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 시큐리티 컨텍스트 홀더에 인증 결과를 집어넣는다.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // 남은 필터가 있다면 계속 진행한다.
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext(); // 반드시 호출!
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
