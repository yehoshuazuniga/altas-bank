package com.atlas.back.transaction.service;

import com.atlas.back.domain.model.account.Account;
import com.atlas.back.account.service.IAccountService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AuditableAccountService implements IAccountService {

    private IAccountService delegate;

    public AuditableAccountService(@Qualifier("accountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    public Account create(Account account) {
        log.info("Creando cuenta — número: {}, titular: {}",
                account.getAccountNumber(), account.getOwnerName());

        Account created = delegate.create(account);

        log.info("Cuenta creada exitosamente — ID: {}", created.getId());

        return created;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(Long id) {
        return delegate.findById(id);
    }
}