package com.atlas.back.transaction.service.fee;

import com.atlas.back.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class CheckingFeeCalculator implements FeeCalcuilator {
    @Override
    public boolean supports(AccountType accountType) {
        return accountType == AccountType.CHECKING;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.015"));
    }
}
