package com.example.budget_manager.transaction;

import com.example.budget_manager.transaction.DTO.CreateTransactionRequest;
import com.example.budget_manager.transaction.DTO.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(request));}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<TransactionResponse> getAll(@RequestParam(required = false)LocalDate from,@RequestParam(required = false)LocalDate to, @RequestParam(required = false) String category) {
        return transactionService.getAllTransactions(from, to, category);
    }
}

