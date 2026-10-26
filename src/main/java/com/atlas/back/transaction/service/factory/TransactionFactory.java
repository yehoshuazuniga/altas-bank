package com.atlas.back.transaction.service.factory;

import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.transaction.model.TransactionStatus;
import com.atlas.back.transaction.model.TransactionType;
import com.atlas.back.transaction.model.state.PendingState;
import com.atlas.back.transaction.service.transfer.TransferContext;

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
