package com.example.security.security;

import com.example.security.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class JwtFactoryTest {
    private AccountContext context;

    @Autowired
    private JwtFactory factory;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        log.info("userid: {}, password: {}, role: {}", account.getUserId(), account.getPassword(), account.getUserRole());
        this.context = AccountContext.fromAccountModel(account);
    }

    @Test
    public void TEST_JWT_GENERATE() {
        log.info(factory.generateToken(context));
    }
}
