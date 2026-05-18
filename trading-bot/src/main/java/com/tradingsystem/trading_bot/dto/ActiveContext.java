package com.tradingsystem.trading_bot.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActiveContext(
    @NotBlank String strategy,
    @NotNull BigDecimal priceLimit
) {
    
}
