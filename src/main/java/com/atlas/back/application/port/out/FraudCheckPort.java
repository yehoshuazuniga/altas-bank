package com.atlas.back.application.port.out;

import com.atlas.back.transaction.service.fraud.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudCheckPort {
    FraudCheckResult check(Long accountId, BigDecimal amount);

}
