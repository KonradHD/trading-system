package com.tradingsystem.trading_bot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BotActiveWalletsMessage(
    @NotNull String action,
    @NotNull Long walletId,
    @NotNull @Valid ActiveContext context
) {}