package com.tradingsystem.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record WalletKeysResponse(
    @NotEmpty List<WalletKeysDTO> keys
) {
    public static WalletKeysResponse createWalletKeysResponse(List<WalletKeysDTO> keys){
        return new WalletKeysResponse(keys);
    }
}
