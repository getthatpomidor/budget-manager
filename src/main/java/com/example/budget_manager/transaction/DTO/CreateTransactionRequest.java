package com.example.budget_manager.transaction.DTO;

import com.example.budget_manager.transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than 0")
        BigDecimal amount,
        @NotNull(message = "Transaction type is required") TransactionType transactionType,
        @NotBlank(message = "Category cannot be empty") String category,
        String description,
        @NotNull(message = "Transaction date is required") LocalDate transactionDate,
        @NotNull(message = "Account id is required") Long accountId

        ) {
}
