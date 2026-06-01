package com.tradingsystem.trading_bot.dto;

import jakarta.validation.constraints.NotNull;

public record ActiveWallet(
    @NotNull Long walletId,
    @NotNull ActiveContext context
) {
    public static ActiveWallet createActiveWallet(Long walletId, ActiveContext context){
        return new ActiveWallet(walletId, context);
    }
}
