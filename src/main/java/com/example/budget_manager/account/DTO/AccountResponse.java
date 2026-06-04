package com.example.budget_manager.account.DTO;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String name,
        BigDecimal balance
) {
}
