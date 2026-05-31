package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletKeysDTO(
    @NotNull Long walletId,
    @NotBlank String apiKey,
    @NotBlank String secretKey
) {
}
