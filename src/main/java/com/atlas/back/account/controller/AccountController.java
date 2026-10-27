package com.atlas.back.account.controller;

import com.atlas.back.account.dto.AccountMapper;
import com.atlas.back.account.dto.AccountResponse;
import com.atlas.back.account.dto.CreateAccountRequest;
import com.atlas.back.account.dto.DashboardResponse;
import com.atlas.back.domain.model.account.Account;
import com.atlas.back.account.service.AccountDashboardFacade;
import com.atlas.back.account.service.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;

    @GetMapping("/{id}/dasboard")
    public ResponseEntity<DashboardResponse> getDasboardAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountDashboardFacade.getDashboard(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);

        Account saved = accountService.create(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> accounts = accountService.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toResponse(accountService.findById(id)));
    }


}