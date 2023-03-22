package com.example.security.security;

import com.example.security.security.filters.FormLoginFilter;
import com.example.security.security.handlers.FormLoginAuthenticationFailureHandler;
import com.example.security.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.example.security.security.providers.FormLoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private FormLoginAuthenticationProvider formLoginAuthenticationProvider;

    @Autowired
    private FormLoginAuthenticationFailureHandler failureHandler;

    @Autowired
    private FormLoginAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //session 생성하지 않도록 설정
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // provider 및 filter 등록
        http
                .addFilterBefore(getLoginFiler(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(formLoginAuthenticationProvider);

        return http.build();
    }

    protected FormLoginFilter getLoginFiler() {
        FormLoginFilter filter = new FormLoginFilter("/formlogin", successHandler, failureHandler);
        return filter;
    }
}
