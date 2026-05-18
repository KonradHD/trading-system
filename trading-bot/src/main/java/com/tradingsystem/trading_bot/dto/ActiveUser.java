package com.tradingsystem.trading_bot.dto;

import jakarta.validation.constraints.NotNull;

public record ActiveUser(
    @NotNull Long userId,
    @NotNull ActiveContext context
) {
    public static ActiveUser createActiveUser(Long userId, ActiveContext context){
        return new ActiveUser(userId, context);
    }
}
