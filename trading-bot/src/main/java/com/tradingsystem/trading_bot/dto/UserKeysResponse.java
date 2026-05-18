package com.tradingsystem.trading_bot.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record UserKeysResponse(
    @NotEmpty List<UserKeysDTO> keys
) {
    public static UserKeysResponse createUserKeysResponse(List<UserKeysDTO> keys){
        return new UserKeysResponse(keys);
    }
}