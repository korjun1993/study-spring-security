package com.example.security;

import com.example.security.security.filters.FilterSkipMatcher;
import com.example.security.security.filters.FormLoginFilter;
import com.example.security.security.filters.JwtAuthenticationFilter;
import com.example.security.security.handlers.FormLoginAuthenticationFailureHandler;
import com.example.security.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.example.security.security.handlers.JwtAuthenticationFailureHandler;
import com.example.security.security.providers.FormLoginAuthenticationProvider;
import com.example.security.security.providers.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final FormLoginAuthenticationProvider formLoginAuthenticationProvider;
    private final FormLoginAuthenticationFailureHandler failureHandler;
    private final FormLoginAuthenticationSuccessHandler successHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationFailureHandler jwtFailureHandler;
    private final HeaderTokenExtractor extractor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //session 생성하지 않도록 설정
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // provider 및 filter 등록
        http
                .addFilterBefore(getLoginFiler(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(formLoginAuthenticationProvider)
                .authenticationProvider(jwtAuthenticationProvider);

        return http.build();
    }

    protected FormLoginFilter getLoginFiler() {
        FormLoginFilter filter = new FormLoginFilter("/formlogin", successHandler, failureHandler);
        return filter;
    }

    protected JwtAuthenticationFilter getJwtFilter() {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("/formlogin"), "/api/**");
        return new JwtAuthenticationFilter(matcher, jwtFailureHandler, extractor);
    }
}
