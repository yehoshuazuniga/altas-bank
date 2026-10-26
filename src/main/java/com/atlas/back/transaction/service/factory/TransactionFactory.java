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
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setCreatedBy("SYSTEM");
        transaction.setSourceAccountId(ctx.from().getId());
        transaction.setTargetAccountId(ctx.to().getId());
        transaction.setAmount(ctx.amount());
        transaction.setFee(fee);
        transaction.setStatus(TransactionStatus.PENDING);

        transaction.advanceTo(new PendingState());
        return transaction;
    }

}
