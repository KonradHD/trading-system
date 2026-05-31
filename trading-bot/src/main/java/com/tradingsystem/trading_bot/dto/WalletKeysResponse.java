package com.tradingsystem.trading_bot.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record WalletKeysResponse(
    @NotEmpty List<WalletKeysDTO> keys
) {
    public static WalletKeysResponse createUserKeysResponse(List<WalletKeysDTO> keys){
        return new WalletKeysResponse(keys);
    }
}