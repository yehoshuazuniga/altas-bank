package com.atlas.back.transaction.service;

import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService {

    private final TransactionRepository transactionRepository;
@Override
    public List<Transaction> getByAccountid(Long accountId) {
        return transactionRepository
                .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
