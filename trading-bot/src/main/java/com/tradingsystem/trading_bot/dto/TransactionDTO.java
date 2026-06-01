package com.tradingsystem.trading_bot.dto;

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
    public static TransactionDTO fromBinanceTransactionDTO(Long transactionId, Long walletId, BinanceTransactionDTO transaction) {
        return new TransactionDTO(
                transactionId,
                walletId,
                transaction.getPriceQty(),
                transaction.getQuantity(),
                transaction.getSymbol(),
                transaction.getStatus(),
                transaction.getTimestamp()
        );
    }
}
