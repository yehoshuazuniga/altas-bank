package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import com.atlas.bank.domain.model.transaction.Transaction;
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
        Transaction transaction = transferService
                .transfer(transferRequest.getFromAccountId(),
                        transferRequest.getToAccountId(),
                        transferRequest.getAmount());



        return ResponseEntity.ok(transactionMapper.toResponse(transaction));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransaction(@PathVariable Long id) {
        List<TransactionResponse> responses = queryService.getByAccountid(id).stream()
                .map(transactionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
