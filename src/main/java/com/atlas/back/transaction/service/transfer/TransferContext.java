package com.atlas.back.transaction.service.transfer;

import com.atlas.back.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account from, Account to, BigDecimal amount)  {
}
