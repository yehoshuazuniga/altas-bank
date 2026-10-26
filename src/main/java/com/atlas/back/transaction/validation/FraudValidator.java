package com.atlas.back.transaction.validation;

import com.atlas.back.transaction.service.exception.FraudCheckException;
import com.atlas.back.transaction.service.fraud.FraudCheckResult;
import com.atlas.back.transaction.service.fraud.FraudChecker;
import com.atlas.back.transaction.service.transfer.TransferContext;
import com.atlas.back.transaction.validation.chain.TransferValidation;
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

        FraudCheckResult fraudCheckResult = fraudChecker.check(ctx.from().getId(), ctx.to().getBalance());

        if(fraudCheckResult.blocked()){
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
