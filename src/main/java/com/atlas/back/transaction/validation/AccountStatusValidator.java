package com.atlas.back.transaction.validation;

import com.atlas.back.domain.model.account.AccountStatus;
import com.atlas.back.domain.exception.AccountNotActiveException;
import com.atlas.back.transaction.service.transfer.TransferContext;
import com.atlas.back.transaction.validation.chain.TransferValidation;
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
