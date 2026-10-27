package com.atlas.back.transaction.service.fee;

import com.atlas.back.domain.model.account.AccountType;

import java.math.BigDecimal;

public interface FeeCalcuilator {
    boolean supports(AccountType accountType);
    BigDecimal calculate(BigDecimal amount);


}
