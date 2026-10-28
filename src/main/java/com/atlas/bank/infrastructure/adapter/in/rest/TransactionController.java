package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.application.query.TransactionReadModel;
import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransferMoneyUseCase transferService;
    private final GetTransactionByAccountUseCase queryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody @Valid TransferRequest transferRequest) {

        TransferMoneyCommand command = TransferMoneyCommand.builder()
                .fromId(transferRequest.getFromAccountId())
                .toId(transferRequest.getToAccountId())
                .amount(transferRequest.getAmount())
                .build();

        Transaction transaction = transferService
                .transfer(command);

        return ResponseEntity.ok(transactionMapper.toResponse(transaction));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionReadModel>> getTransaction(@PathVariable Long id) {

        GetAccountStatementQuery query = new GetAccountStatementQuery(id);

        List<TransactionReadModel> responses = queryService.getByAccountId(query);
        return ResponseEntity.ok(responses);
    }
}
