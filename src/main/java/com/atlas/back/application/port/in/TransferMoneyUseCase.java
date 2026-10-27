package com.atlas.back.application.port.in;

import com.atlas.back.transaction.model.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction execute(Long fromId, Long toId, BigDecimal amount);

}
