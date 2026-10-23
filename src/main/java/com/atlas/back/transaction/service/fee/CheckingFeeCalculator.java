package com.atlas.back.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CheckingFeeCalculator implements FeeCalcuilator {
    @Override
    public boolean supports(String accountType) {
        return "CHECKING".equalsIgnoreCase(accountType);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.015"));
    }
}
