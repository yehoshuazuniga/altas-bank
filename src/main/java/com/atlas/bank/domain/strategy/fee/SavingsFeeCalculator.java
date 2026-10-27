package com.atlas.bank.domain.strategy.fee;

import com.atlas.bank.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class SavingsFeeCalculator implements FeeCalcuilator {
    @Override
    public boolean supports(AccountType accountType) {
        return accountType.equals(AccountType.SAVING);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01"));
    }
}
