package com.atlas.back.application.port.out;

import com.atlas.back.domain.model.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountrepositoryPort {
    Optional<Account> findById(Long id);

    List<Account> findAll();

    Account save(Account account);
}
