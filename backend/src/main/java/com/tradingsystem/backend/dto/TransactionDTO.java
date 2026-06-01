package com.tradingsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(
        @NotNull Long transactionId,
        @NotNull Long walletId,
        @NotNull BigDecimal priceQty,
        @NotNull Double quantity,
        @NotBlank String symbol,
        @NotBlank String status,
        @NotNull Long timestamp
) {
}
