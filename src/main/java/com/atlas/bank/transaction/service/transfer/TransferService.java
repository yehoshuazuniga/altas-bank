package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.application.port.out.AccountrepositoryPort;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.transaction.service.domain.TransferDomainService;
import com.atlas.bank.transaction.service.factory.TransactionFactory;
import com.atlas.bank.transaction.service.fee.FeeCalcuilator;
import com.atlas.bank.transaction.validation.chain.TransferValidation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService , TransferMoneyUseCase {

    private final AccountrepositoryPort accountRepository;
    private final List<FeeCalcuilator> feeCalcuilators;
    private final List<TransferValidation> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(TransactionRepository transactionRepository, AccountrepositoryPort accountRepository,
                           List<FeeCalcuilator> feeCalcuilators, ApplicationEventPublisher eventPublisher,
                           List<TransferValidation> validators, TransferDomainService transferDomainService) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalcuilators = feeCalcuilators;
        this.validators = validators;
        this.transferDomainService = transferDomainService;
    }

    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));

        transaction.executeTranfer();
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    protected void validate(TransferContext ctx) {

        validators.forEach(validator -> validator.validate(ctx));
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

//        BigDecimal newFromBalance = ctx.from().getBalance().getAmount().subtract(ctx.amount()).subtract(fee);
//
//        // Actualizar saldos
//        ctx.from().setBalance(Money.of(newFromBalance, ctx.from().getBalance().getCurrency()));
//
//        BigDecimal newToBalance = ctx.to().getBalance().getAmount().add(ctx.amount());
//        ctx.to().setBalance(Money.of(newToBalance, ctx.to().getBalance().getCurrency()));

        transferDomainService.trasnfer(ctx.from(), ctx.to(),  ctx.amount(),fee);
        accountRepository.save(ctx.from());
        accountRepository.save(ctx.to());
    }

    @Override
    protected Transaction save(TransferContext ctx, BigDecimal fee) {

        Transaction transaction = TransactionFactory.createTarnsfer(ctx, fee);

        return transactionRepository.save(transaction);
    }
}
