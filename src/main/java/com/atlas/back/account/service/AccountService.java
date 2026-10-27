package com.atlas.back.account.service;

import com.atlas.back.domain.model.account.Account;
import com.atlas.back.account.repository.DomainAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final DomainAccountRepository accountRepository;

    @Override
    @Transactional
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="accounts", key="#id")
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }


}