package com.example.budget_manager.account;

import com.example.budget_manager.account.DTO.AccountResponse;
import com.example.budget_manager.account.DTO.CreateAccountRequest;
import com.example.budget_manager.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;
    private final TransactionService transactionService;

    public AccountController(AccountService service,TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;
    }

    @Operation(summary="Create Account", description = "Creates a new acount with zero balance")
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(request));
    }

    @Operation(summary = "Get all accounts", description = "Lists all accounts")
    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return service.getAllAccounts();
    }

    @Operation(summary = "Get account details", description = "Retrieves details of account with given id")
    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Delete account", description = "Deletes account")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get account transactions", description = "Lists account transactions")
    @GetMapping("/{id}/transactions/export")
    public ResponseEntity<byte[]> exportTransactions(@PathVariable Long id) {
        byte[] bytes = transactionService.exportTransactions(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv").contentType(MediaType.TEXT_PLAIN).body(bytes);
    }
}
