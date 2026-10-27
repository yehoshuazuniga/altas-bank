package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  SpringDataTransactionRepository extends JpaRepository<TransactionJpaEntity, Integer> {
    List<TransactionJpaEntity> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);

}
