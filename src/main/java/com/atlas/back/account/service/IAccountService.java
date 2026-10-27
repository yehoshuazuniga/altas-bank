package com.atlas.back.account.service;


import com.atlas.back.domain.model.account.Account;

import java.util.List;

public interface IAccountService {
    Account create(Account account);

    List<Account> findAll();

    Account findById(Long id);

}