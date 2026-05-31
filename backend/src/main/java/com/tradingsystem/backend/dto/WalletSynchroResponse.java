package com.tradingsystem.backend.dto;

import com.tradingsystem.backend.model.Wallet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletSynchroResponse(
        @NotNull Long walletId,
        @NotBlank String status,
        @NotBlank String message,
        @NotNull BigDecimal newWalletFunds
) {
    public static WalletSynchroResponse createWalletSynchroResponse(Wallet wallet, String status, String message) {
        return new WalletSynchroResponse(wallet.getId(), status, message, wallet.getAvailableFunds())   ;
    }
}
