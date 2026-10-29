package com.atlas.bank.domain.model.account;


import com.atlas.bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.domain.model.shared.Email;
import com.atlas.bank.domain.model.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountDomainTest {

    private Account createAccountWithBalance(BigDecimal balance){

        return Account.builder()
                .id(1L)
                .accountNumber("ATL-0001-0001-0001")
                .ownerName("María García")
                .email(Email.of("maria@atlas.bank"))
                .type(AccountType.SAVING)
                .balance(Money.of(balance, Currency.EUR))
                .status(AccountStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Debe depositar dinero correctamente")
    void shouldDepositMoney(){
        Account account = createAccountWithBalance(new BigDecimal("1000"));
        Money deposit = Money.of(new BigDecimal("500"), Currency.EUR);

        account.deposit(deposit);

        assertEquals(
                Money.of(new BigDecimal("1500"), Currency.EUR),
                account.getBalance()
        );
    }

    @Test
    @DisplayName("Debe retirar dinero cuando hay fondos suficientes")
    void shouldWithdrawWhenSufficientFunds() {
        // Arrange
        Account account = createAccountWithBalance(new BigDecimal("1000"));
        Money withdrawal = Money.of(new BigDecimal("300"), Currency.EUR);

        account.withdraw(withdrawal);

        assertEquals(
                Money.of(new BigDecimal("700.00"), Currency.EUR),
                account.getBalance()
        );
    }

    @Test
    @DisplayName("Debe rechazar retiro cuando no hay fondos suficientes")
    void shouldRejectWithdrawWhenInsufficientFunds() {
        // Arrange
        Account account = createAccountWithBalance(new BigDecimal("100"));
        Money withdrawal = Money.of(new BigDecimal("500"), Currency.EUR);

        assertThrows(
                InsufficientFundsException.class, () ->
                        account.withdraw(withdrawal)
        );
    }

    @Test
    @DisplayName("Debe rechazar depósito con monto negativo")
    void shouldRejectNegativeDeposit() {
        // Arrange
        Account account = createAccountWithBalance(new BigDecimal("1000"));
        Money negative = Money.of(new BigDecimal("-100"), Currency.EUR);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                account.deposit(negative)
        );
    }

    @Test
    @DisplayName("Debe cerrar una cuenta activa sin saldo")
    void shouldCloseActiveAccountWithZeroBalance() {
        // Arrange
        Account account = createAccountWithBalance(BigDecimal.ZERO);

        // Act
        account.close();

        // Assert
        assertEquals(AccountStatus.CLOSED, account.getStatus());
    }

    @Test
    @DisplayName("Debe rechazar cierre cuando la cuenta tiene saldo")
    void shouldRejectCloseWhenAccountHasBalance() {
        // Arrange
        Account account = createAccountWithBalance(new BigDecimal("100.00"));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, account::close);
        assertEquals("No se puede cerrar una cuenta con saldo", exception.getMessage());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
    }

    @Test
    @DisplayName("Debe rechazar cierre cuando la cuenta no está activa")
    void shouldRejectCloseWhenAccountIsNotActive() {
        // Arrange
        Account account = createAccountWithBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.CLOSED);

        // Act & Assert
        assertThrows(AccountNotActiveException.class, account::close);
    }

    @Test
    @DisplayName("Debe inicializar con valores por defecto")
    void shouldInitializeDefaults() {
        // Arrange
        Account account = Account.builder()
                .accountNumber("ATL-0002-0002-0002")
                .ownerName("Juan Pérez")
                .email(Email.of("juan@atlas.bank"))
                .type(AccountType.CHECKING)
                .build();

        account.initDefaults();

        assertEquals(AccountStatus.ACTIVE, account.getStatus());
        assertEquals(Money.zero(Currency.EUR), account.getBalance());
        assertNotNull(account.getCreatedAt());

    }

}