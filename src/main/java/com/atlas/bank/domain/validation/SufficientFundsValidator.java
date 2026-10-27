package com.atlas.bank.domain.validation;

import com.atlas.bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.domain.model.transaction.TransferContext;


public class SufficientFundsValidator implements TransferValidation {
    @Override
    public void validate(TransferContext ctx) {
        // Validar fondos
        if (ctx.from().getBalance().getAmount().compareTo(ctx.amount()) < 0) {
            throw new InsufficientFundsException(ctx.from().getId(), ctx.from().getBalance().getAmount(), ctx.amount());
        }
    }
}
