package com.atlas.bank.domain.strategy.fee;


import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;


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
