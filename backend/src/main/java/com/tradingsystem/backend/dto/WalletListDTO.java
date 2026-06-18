package com.tradingsystem.backend.dto;

import com.tradingsystem.backend.model.Wallet;

import java.util.List;

public record WalletListDTO(
        List<WalletDTO> wallets
) {
    public static WalletListDTO createWalletsListDTO(List<Wallet> wallets){
        return new WalletListDTO(
                wallets.stream()
                        .map(wallet -> new WalletDTO(
                                wallet.getId(),
                                wallet.getName(),
                                wallet.getIsActive(),
                                wallet.getAvailableFunds(),
                                wallet.getActiveTrades()
                        ))
                        .toList()
        );
    }
}
