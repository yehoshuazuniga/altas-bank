package com.atlas.bank.application.service;

import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.domain.model.transaction.TransactionStatus;
import com.atlas.bank.domain.model.transaction.TransactionType;
import com.atlas.bank.domain.model.transaction.state.PendingState;
import com.atlas.bank.domain.model.transaction.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTarnsfer(TransferContext ctx, BigDecimal fee) {
        // Crear transacción
        Transaction transaction =  Transaction.builder()
                .type(TransactionType.TRANSFER)
                .createdBy("SYSTEM")
                .sourceAccountId(ctx.from().getId())
                .targetAccountId(ctx.to().getId())
                .amount(ctx.amount())
                .fee(fee)
                .status(TransactionStatus.PENDING).build();

        transaction.advanceTo(new PendingState());
        return transaction;
    }

}
