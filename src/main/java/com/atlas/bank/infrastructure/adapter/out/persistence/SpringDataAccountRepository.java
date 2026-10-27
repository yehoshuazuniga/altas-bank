package com.atlas.bank.infrastructure.adapter.out.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountRepository
        extends JpaRepository<AccountJpaEntity, Long> {
}