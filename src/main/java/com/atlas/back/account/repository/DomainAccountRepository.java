package com.atlas.back.account.repository;

import com.atlas.back.domain.model.account.Account;

import java.util.List;
import java.util.Optional;

public interface DomainAccountRepository {
    Optional<Account> findById(Long id);

    List<Account> findAll();

    Account save(Account account);
}

