package com.atlas.back.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DeafultFeeCalculator implements  FeeCalcuilator {
    @Override
    public boolean supports(String accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
