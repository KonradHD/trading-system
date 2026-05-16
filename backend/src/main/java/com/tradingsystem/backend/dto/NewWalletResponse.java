package com.tradingsystem.backend.dto;

import java.time.LocalDateTime;

import com.tradingsystem.backend.model.Wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record NewWalletResponse (
    @Schema(description = "Unique identifier of the wallet") @NotNull Long id,
    @Schema(description = "Unique user identifier number") Long userId,
    @Schema(description = "The name of the wallet") @NotBlank String name,
    @Schema(description = "Timestamp of the wallet creation") @PastOrPresent LocalDateTime createdAt
) {
    public static NewWalletResponse createWalletResponse(Wallet wallet){
        return new NewWalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getName(), wallet.getCreatedAt());
    }
}