package com.atlas.back.account.repository;

import com.atlas.back.domain.model.account.Account;
import com.atlas.back.application.port.out.AccountrepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, Long>, DomainAccountRepository, AccountrepositoryPort {
}