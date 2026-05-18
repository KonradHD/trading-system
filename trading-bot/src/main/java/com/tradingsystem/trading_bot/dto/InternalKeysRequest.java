package com.tradingsystem.trading_bot.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record InternalKeysRequest(
    @NotEmpty(message = "Request for keys cannot be empty.")  List<Long> userIds
) {
    
}