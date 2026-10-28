package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.command.CreateAccountCommand;
import com.atlas.bank.application.facade.AccountDashboardFacade;
import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountuseCase;
import com.atlas.bank.application.query.DashboardReadModel;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
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

    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountuseCase listAccountuseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;

    @GetMapping("/{id}/dasboard")
    public ResponseEntity<DashboardReadModel> getDasboardAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountDashboardFacade.getDashboard(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        CreateAccountCommand command = CreateAccountCommand.builder()
                .accountNumber(request.getAccountNumber())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .type(request.getType())
                .balance(request.getBalance())
                .build();

        Account saved = createAccountUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> accounts = listAccountuseCase.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toResponse(getAccountUseCase.findById(id)));
    }


}