package com.atlas.bank.application.service;

import com.atlas.bank.application.command.CloseAccountCommand;
import com.atlas.bank.application.command.CreateAccountCommand;
import com.atlas.bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountuseCase;
import com.atlas.bank.application.port.out.AccountrepositoryPort;
import com.atlas.bank.domain.event.AccountClosedEvent;
import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.domain.model.shared.Email;
import com.atlas.bank.domain.model.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase , ListAccountuseCase, GetAccountUseCase, CloseAccountUseCase {

    private final AccountrepositoryPort accountRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Account create(CreateAccountCommand command) {
        Account account = Account.builder()
                .accountNumber(command.accountNumber())
                .ownerName(command.ownerName())
                .email(Email.of(command.email()))
                .type(command.type())
                .balance(Money.of(command.balance(), Currency.EUR))
                .build();

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

    @Override
    @Transactional
    @CacheEvict(value="accounts", key="#command.accountId()")
    public Account close(CloseAccountCommand command) {
        Account account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new AccountNotFoundException(command.accountId()));

        account.close();

        Account saved = accountRepository.save(account);
        eventPublisher.publishEvent(new AccountClosedEvent(saved.getId(), saved.getAccountNumber()));

        return saved;
    }


}