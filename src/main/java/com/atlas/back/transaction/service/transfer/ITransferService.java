package com.atlas.back.transaction.service.transfer;

import com.atlas.back.transaction.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromId, Long toId, BigDecimal amount);

}
