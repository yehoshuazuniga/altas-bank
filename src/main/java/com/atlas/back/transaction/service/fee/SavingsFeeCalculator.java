package com.atlas.back.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SavingsFeeCalculator implements FeeCalcuilator {
    @Override
    public boolean supports(String accountType) {
        return "SAVINGS".equalsIgnoreCase(accountType);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01"));
    }
}
