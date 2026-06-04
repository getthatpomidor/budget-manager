package com.example.budget_manager.account.DTO;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateAccountRequest(@NotBlank(message = "Name cannot be blank") String name) {
}
