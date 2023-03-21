package com.example.security.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository {
    Optional<Account> findBuyUserId(String userId);

    Optional<Account> findBySocialId(Long socialId);
}
