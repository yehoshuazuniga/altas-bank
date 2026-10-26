package com.atlas.back.account.service;

import com.atlas.back.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Primary
public class CachedAccountService implements IAccountService {

    private final IAccountService delegate;
    private final Map<Long, Account> cached = new ConcurrentHashMap<>();

    public CachedAccountService( @Qualifier("auditableAccountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Account create(Account account) {

        Account created =delegate.create(account);
        cached.put(created.getId(), created);
        log.info("Cuenta {} agregada al cache", created.getId());
        return created;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(Long id) {

        Account chached = cached.get(id);

        if(chached != null) {
            log.info("Cuenta  encontrada en cache - NO se llama al service real{}", id);
            return chached;
        }
        log.info("Cuenta {} no esta en cache -delegando al service real", id);

        Account account = delegate.findById(id);
        cached.put(id, account);
        return account;
    }
}
