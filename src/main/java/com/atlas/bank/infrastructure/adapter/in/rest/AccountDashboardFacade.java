package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final GetAccountUseCase accountService;
    private final GetTransactionByAccountUseCase transactionQueryService;
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
