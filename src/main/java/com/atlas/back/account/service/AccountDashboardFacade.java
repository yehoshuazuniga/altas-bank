package com.atlas.back.account.service;

import com.atlas.back.account.dto.DashboardResponse;
import com.atlas.back.domain.model.account.Account;
import com.atlas.back.transaction.dto.TransactionMapper;
import com.atlas.back.transaction.dto.TransactionResponse;
import com.atlas.back.transaction.service.ITransactionQueryService;
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
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(transaction)
                .build()
                ;

    }


}
