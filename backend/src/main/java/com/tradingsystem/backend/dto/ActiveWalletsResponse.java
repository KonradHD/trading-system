package com.tradingsystem.backend.dto;

import java.util.List;

public record ActiveWalletsResponse(
    List<ActiveWallet> activeWallets
) {
    public static ActiveWalletsResponse createActiveWalletsResponse(List<ActiveWallet> activeWallets){
        return new ActiveWalletsResponse(activeWallets);
    }
}
