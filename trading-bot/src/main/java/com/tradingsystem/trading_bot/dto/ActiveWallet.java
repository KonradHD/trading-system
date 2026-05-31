package com.tradingsystem.trading_bot.dto;

import jakarta.validation.constraints.NotNull;

public record ActiveWallet(
    @NotNull Long userId,
    @NotNull ActiveContext context
) {
    public static ActiveWallet createActiveUser(Long walletId, ActiveContext context){
        return new ActiveWallet(walletId, context);
    }
}
