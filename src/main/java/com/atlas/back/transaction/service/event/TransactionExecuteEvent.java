package com.atlas.back.transaction.service.event;

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
