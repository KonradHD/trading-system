package com.tradingsystem.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BotActiveUsersMessage(
    @NotNull String action,
    @NotNull Long userId,
    @NotNull @Valid ActiveContext context
) {}
