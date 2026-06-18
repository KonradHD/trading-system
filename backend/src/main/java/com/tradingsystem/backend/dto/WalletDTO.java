package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WalletDTO (
    @NotNull Long walletId,
    @NotBlank String walletName,
    @NotNull Boolean isActive,
    @NotNull @PositiveOrZero
    BigDecimal availableFunds,
    @NotNull Boolean activeTrades
){

}
