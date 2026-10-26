package com.atlas.back.transaction.service.domain;

import com.atlas.back.account.model.Account;
import com.atlas.back.shared.model.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferDomainService {

    public void trasnfer (Account from, Account to, BigDecimal amount, BigDecimal fee){

        Money totalDebit = Money.of(amount.add(fee), from.getBalance().getCurrency());
        Money depositAmount = Money.of(amount, to.getBalance().getCurrency());

        from.withdraw(depositAmount);
        to.deposit(depositAmount);


    }


}
