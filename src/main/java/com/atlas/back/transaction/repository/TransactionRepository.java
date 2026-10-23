package com.atlas.back.transaction.repository;

import com.atlas.back.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);

}