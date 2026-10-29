package com.atlas.bank.domain.model.account;

import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.domain.model.shared.Email;
import com.atlas.bank.domain.model.shared.Money;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Account {


    @EqualsAndHashCode.Include
    private Long id;

    private String accountNumber;

    private String ownerName;

    private Email email;

    private AccountType type; //SAVING, CHECKING

    private Money balance;


    private AccountStatus status; //ACTIVE, CLOSED, FROZEN

    private LocalDateTime createdAt;

    private Long customerId;

    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.EUR);
    }

    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("El monto a depositar no puede ser negativo");
        }

        balance = balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("El monto a retirar no puede ser negativo");
        }

        if (balance.isLessThan(amount)) {
            throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
        }

        balance = balance.subtract(amount);
    }

    public void close() {
        if (status != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(id, String.valueOf(status));
        }

        if (balance == null || !balance.isZero()) {
            throw new IllegalStateException("No se puede cerrar una cuenta con saldo");
        }

        this.status = AccountStatus.CLOSED;
    }

    public void initDefaults() {
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.EUR);
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

}