package com.tradingsystem.trading_bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserKeysDTO(
    @NotNull Long userId,
    @NotBlank String apiKey,
    @NotBlank String secretKey
) {

}