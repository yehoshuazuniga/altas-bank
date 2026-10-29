package com.atlas.bank.application.validation;

import com.atlas.bank.application.port.out.FraudCheckPort;
import com.atlas.bank.domain.exception.FraudCheckException;
import com.atlas.bank.domain.model.shared.FraudCheckResult;
import com.atlas.bank.domain.model.transaction.TransferContext;
import com.atlas.bank.domain.validation.TransferValidation;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class FraudValidator implements TransferValidation {
    private final FraudCheckPort fraudChecker;

    @Override
    public void validate(TransferContext ctx) {

        FraudCheckResult fraudCheckResult = fraudChecker.check(ctx.from().getId(), ctx.to().getBalance().getAmount());

        if(fraudCheckResult.blocked()){
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
