package com.atlas.bank.application.service;

import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountuseCase;
import com.atlas.bank.domain.model.account.Account;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, ListAccountuseCase , GetAccountUseCase {

    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountuseCase listAccountuseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AuditableAccountService(@Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
                                   @Qualifier("accountService")ListAccountuseCase listAccountuseCase,
                                   @Qualifier("accountService")GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.listAccountuseCase = listAccountuseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    public Account create(Account account) {
        log.info("Creando cuenta — número: {}, titular: {}",
                account.getAccountNumber(), account.getOwnerName());

        Account created = createAccountUseCase.create(account);

        log.info("Cuenta creada exitosamente — ID: {}", created.getId());

        return created;
    }

    @Override
    public List<Account> findAll() {
        return listAccountuseCase.findAll();
    }

    @Override
    public Account findById(Long id) {
        return getAccountUseCase.findById(id);
    }
}