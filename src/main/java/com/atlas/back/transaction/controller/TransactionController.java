package com.atlas.back.transaction.controller;

import com.atlas.back.account.service.IAccountService;
import com.atlas.back.transaction.dto.TransactionMapper;
import com.atlas.back.transaction.dto.TransactionResponse;
import com.atlas.back.transaction.dto.TransferRequest;
import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.transaction.service.ITransactionQueryService;
import com.atlas.back.transaction.service.ITransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransferService transferService;
    private final ITransactionQueryService queryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        Transaction transaction = transferService
                .execute(transferRequest.getFromAccountId(),
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
