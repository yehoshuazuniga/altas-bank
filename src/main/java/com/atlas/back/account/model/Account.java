package com.atlas.back.account.model;

import com.atlas.back.shared.model.Currency;
import com.atlas.back.shared.model.Money;
import com.atlas.back.transaction.exception.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type; //SAVING, CHECKING

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false)),
                    @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, length = 30)),

            }
    )
    @Column(nullable = false)
    private Money balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status; //ACTIVE, CLOSED, FROZEN

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "customer_id")
    private Long customerId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.EUR);
    }

    public void deposit(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("El monto a depositar no puede ser negativo");
        }

        balance = balance.add(amount);
    }

    public void withdraw(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("El monto a retirar no puede ser negativo");
        }

        if(balance.isLessThan(amount)){
            throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
        }

        balance = balance.subtract(amount);
    }
}