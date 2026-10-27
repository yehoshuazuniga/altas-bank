package com.atlas.bank.shared.model;

import com.atlas.bank.domain.model.shared.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Money {

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        if(amount == null){
            throw new IllegalArgumentException("El monto nio puede ser nulo");
        }
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currency currency){
        return new Money(amount, currency);
    }

    public static Money zero(Currency currency){
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other){
        validateSameCurrency(other);
        return  Money.of(this.amount.subtract(other.amount), this.currency);
    }

    public boolean isGreatherThan(Money other){
        validateSameCurrency(other);
    return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isNegative (){
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isLessThan(Money other){
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) < 0;
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);
        return Money.of(this.amount.subtract(other.amount), this.currency);
    }

    private void validateSameCurrency(Money other){
        if(currency != other.currency){
            throw new IllegalArgumentException("No se pueden operar montos en monedas distintas: "
                    + this.currency + " vs " + other.currency);
        }
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency;
    }
}
