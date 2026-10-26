package com.atlas.back.transaction.validation;

import com.atlas.back.transaction.exception.InsufficientFundsException;
import com.atlas.back.transaction.service.transfer.TransferContext;
import com.atlas.back.transaction.validation.chain.TransferValidation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SufficentFundsValidator implements TransferValidation {
    @Override
    public void validate(TransferContext ctx) {
        // Validar fondos
        if (ctx.from().getBalance().getAmount().compareTo(ctx.amount()) < 0) {
            throw new InsufficientFundsException(ctx.from().getId(), ctx.from().getBalance().getAmount(), ctx.amount());
        }
    }
}
