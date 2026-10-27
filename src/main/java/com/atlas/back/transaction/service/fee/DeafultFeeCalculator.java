package com.atlas.back.transaction.service.fee;

import com.atlas.back.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order()
public class DeafultFeeCalculator implements  FeeCalcuilator {
    @Override
    public boolean supports(AccountType accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
