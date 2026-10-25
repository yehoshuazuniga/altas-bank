package com.atlas.back.transaction.service.fee;


import com.atlas.back.account.model.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Order(1)
public class PremiumFeeCalculator implements  FeeCalcuilator{


    @Override
    public boolean supports(AccountType accountType) {
        return accountType.equals(AccountType.PREMIUM);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
