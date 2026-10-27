package com.atlas.back.account.repository;

import com.atlas.back.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, DomainAccountRepository {
}