package com.atlas.back.account.service;

import com.atlas.back.account.dto.AccountMapper;
import com.atlas.back.account.dto.DashboardResponse;
import com.atlas.back.account.model.Account;
import com.atlas.back.transaction.dto.TransactionMapper;
import com.atlas.back.transaction.dto.TransactionResponse;
import com.atlas.back.transaction.service.ITransactionQueryService;
import com.atlas.back.transaction.service.fraud.FraudCheckResult;
import com.atlas.back.transaction.service.fraud.FraudChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final IAccountService accountService;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    public DashboardResponse getDashboard(Long accointId){

        Account account = accountService.findById(accointId);

        List<TransactionResponse> transaction = transactionQueryService
                .getByAccountid(accointId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();


        return DashboardResponse.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .recentTransactions(transaction)
                .build()
                ;

    }


}
