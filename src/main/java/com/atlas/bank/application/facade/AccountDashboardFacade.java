package com.atlas.bank.application.facade;

import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.application.query.DashboardReadModel;
import com.atlas.bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.application.query.TransactionReadModel;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final GetAccountUseCase accountService;
    private final GetTransactionByAccountUseCase transactionQueryService;
    private final TransactionMapper transactionMapper;

    public DashboardReadModel getDashboard(Long accountId){

        Account account = accountService.findById(accountId);

        List<TransactionReadModel> transactions = transactionQueryService
                .getByAccountId(new GetAccountStatementQuery(accountId));


        return DashboardReadModel.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build()
                ;

    }


}
