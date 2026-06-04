package com.example.budget_manager.transaction.DTO;

import com.example.budget_manager.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        BigDecimal amount,
        TransactionType transactionType,
        String category,
        String description,
        LocalDate transactionDate,
        Long accountId
) {
}
