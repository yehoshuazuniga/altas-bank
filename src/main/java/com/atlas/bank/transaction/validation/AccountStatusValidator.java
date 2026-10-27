package com.atlas.bank.transaction.validation;

import com.atlas.bank.domain.model.account.AccountStatus;
import com.atlas.bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.transaction.service.transfer.TransferContext;
import com.atlas.bank.transaction.validation.chain.TransferValidation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AccountStatusValidator implements TransferValidation {
    @Override
    public void validate(TransferContext ctx) {
        // Validar que la cuenta esté activa
        if (ctx.from().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(ctx.from().getId(), ctx.from().getStatus().name());
        }
        if (ctx.to().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(ctx.to().getId(), ctx.to().getStatus().name());
        }

    }



}
