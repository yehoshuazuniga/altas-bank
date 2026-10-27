package com.atlas.bank.transaction.service.event;

import java.math.BigDecimal;

public record TransactionExecuteEvent(
        Long transactionId,
        String type,
        Long sourceAccountId,
        Long tragetAccountId,
        BigDecimal amount,
        BigDecimal fee
) {
}
