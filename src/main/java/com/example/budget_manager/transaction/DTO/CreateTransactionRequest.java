package com.example.budget_manager.transaction.DTO;

import com.example.budget_manager.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull
        @Positive(message = "Amount must be greater than 0")
        BigDecimal amount,
        @NotNull TransactionType transactionType,
        @NotNull String category,
        String description,
        @NotNull LocalDate transactionDate,
        @NotNull Long accountId

        ) {
}
