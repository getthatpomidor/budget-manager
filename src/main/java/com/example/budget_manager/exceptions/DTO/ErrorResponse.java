package com.example.budget_manager.exceptions.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(String message, LocalDateTime timestamp) {

}
