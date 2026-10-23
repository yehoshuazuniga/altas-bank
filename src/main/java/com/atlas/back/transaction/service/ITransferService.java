package com.atlas.back.transaction.service;

import com.atlas.back.transaction.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromId, Long toId, BigDecimal amount);

}
