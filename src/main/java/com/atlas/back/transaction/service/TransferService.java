package com.atlas.back.transaction.service;

import com.atlas.back.account.exception.AccountNotFoundException;
import com.atlas.back.account.model.Account;
import com.atlas.back.transaction.exception.AccountNotActiveException;
import com.atlas.back.transaction.exception.InsufficientFundsException;
import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.account.repository.AccountRepository;
import com.atlas.back.transaction.repository.TransactionRepository;
import com.atlas.back.transaction.service.fee.FeeCalcuilator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final List<FeeCalcuilator> feeCalcuilators;

    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        // Validar que la cuenta esté activa
        if (!"ACTIVE".equals(from.getStatus())) {
            throw new AccountNotActiveException(fromId,from.getStatus());
        }
        if (!"ACTIVE".equals(to.getStatus())) {
            throw new AccountNotActiveException(toId,to.getStatus());
        }

        // Validar fondos
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(fromId, from.getBalance(),amount);
        }

        // Calcular comisión — hardcodeada
        BigDecimal fee= feeCalcuilators.stream()
                .filter( fc->fc.supports(from.getType()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException(" No hay calculador para el tipo "+ from.getType()))
                .calculate(amount);

        if ("SAVINGS".equals(from.getType())) {
            fee = amount.multiply(new BigDecimal("0.01"));
        } else if ("CHECKING".equals(from.getType())) {
            fee = amount.multiply(new BigDecimal("0.015"));
        } else {
            fee = BigDecimal.ZERO;
        }

        // Actualizar saldos
        from.setBalance(from.getBalance().subtract(amount).subtract(fee));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(fromId);
        transaction.setTargetAccountId(toId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }

}
