package com.example.security.security;

import com.example.security.domain.Account;
import com.example.security.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AccountContextService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("아이디에 맞는 계정이 없습니다"));
        return getAccountContext(account);
    }

    private AccountContext getAccountContext(Account account) {
        return AccountContext.fromAccountModel(account);
    }
}
