package com.atlas.back.transaction.service;

import com.atlas.back.transaction.model.Transaction;

import java.util.List;

public interface ITransactionQueryService {
    List<Transaction> getByAccountid(Long accountId);
}
