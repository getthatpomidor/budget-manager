package com.example.budget_manager.summary.DTO;

import java.math.BigDecimal;
import java.util.Map;

public record SummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        Map<String,BigDecimal> expenseByCategory
) {}
