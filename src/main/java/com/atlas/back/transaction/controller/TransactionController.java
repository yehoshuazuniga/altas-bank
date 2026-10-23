package com.atlas.back.transaction.controller;

import com.atlas.back.account.service.IAccountService;
import com.atlas.back.transaction.model.Transaction;
import com.atlas.back.transaction.service.ITransactionQueryService;
import com.atlas.back.transaction.service.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransferService transferService;
    private final ITransactionQueryService queryService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transferService.execute(fromId, toId, amount));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(queryService.getByAccountid(id));
    }
}
