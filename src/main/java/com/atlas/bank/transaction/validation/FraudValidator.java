package com.atlas.bank.transaction.validation;

import com.atlas.bank.transaction.service.exception.FraudCheckException;
import com.atlas.bank.transaction.service.fraud.FraudCheckResult;
import com.atlas.bank.transaction.service.fraud.FraudChecker;
import com.atlas.bank.transaction.service.transfer.TransferContext;
import com.atlas.bank.transaction.validation.chain.TransferValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidation {
    private final FraudChecker fraudChecker;

    @Override
    public void validate(TransferContext ctx) {

        FraudCheckResult fraudCheckResult = fraudChecker.check(ctx.from().getId(), ctx.to().getBalance().getAmount());

        if(fraudCheckResult.blocked()){
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
