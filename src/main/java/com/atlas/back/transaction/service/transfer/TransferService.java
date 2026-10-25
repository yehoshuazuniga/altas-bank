package com.atlas.back.transaction.service.transfer;

import com.atlas.back.account.exception.AccountNotFoundException;
import com.atlas.back.account.model.Account;
import com.atlas.back.transaction.exception.AccountNotActiveException;
import com.atlas.back.transaction.exception.InsufficientFundsException;
import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.account.repository.AccountRepository;
import com.atlas.back.transaction.repository.TransactionRepository;
import com.atlas.back.transaction.service.factory.TransactionFactory;
import com.atlas.back.transaction.service.fee.FeeCalcuilator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {

    private final AccountRepository accountRepository;
    private final List<FeeCalcuilator> feeCalcuilators;

    public TransferService(TransactionRepository transactionRepository, AccountRepository accountRepository, List<FeeCalcuilator> feeCalcuilators) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalcuilators = feeCalcuilators;
    }

    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        return process(new TransferContext(from, to, amount));
    }

    @Override
    protected void validate(TransferContext ctx) {
        // Validar que la cuenta esté activa
        if (!"ACTIVE".equals(ctx.from().getStatus())) {
            throw new AccountNotActiveException(ctx.from().getId(), ctx.from().getStatus());
        }
        if (!"ACTIVE".equals(ctx.to().getStatus())) {
            throw new AccountNotActiveException(ctx.to().getId(), ctx.to().getStatus());
        }

        // Validar fondos
        if (ctx.from().getBalance().compareTo(ctx.amount()) < 0) {
            throw new InsufficientFundsException(ctx.from().getId(), ctx.from().getBalance(), ctx.amount());
        }

    }

    @Override
    protected BigDecimal calculateFee(TransferContext ctx) {
        // Calcular comisión — hardcodeada

        return feeCalcuilators.stream()
                .filter(fc -> fc.supports(ctx.from().getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(" No hay calculador para el tipo " + ctx.from().getType()))
                .calculate(ctx.amount());
    }

    @Override
    protected void execute(TransferContext ctx, BigDecimal fee) {
        // Actualizar saldos
        ctx.from().setBalance(ctx.from().getBalance().subtract(ctx.amount()).subtract(fee));
        ctx.to().setBalance(ctx.to().getBalance().add(ctx.amount()));
        accountRepository.save(ctx.from());
        accountRepository.save(ctx.to());
    }

    @Override
    protected Transaction save(TransferContext ctx, BigDecimal fee) {

        Transaction transaction = TransactionFactory.createTarnsfer(ctx, fee);

        return transactionRepository.save(transaction);
    }
}
