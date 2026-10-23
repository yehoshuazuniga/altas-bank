package com.atlas.back.transaction.service.fee;

import java.math.BigDecimal;

public interface FeeCalcuilator {
    boolean supports(String accountType);
    BigDecimal calculate(BigDecimal amount);


}
