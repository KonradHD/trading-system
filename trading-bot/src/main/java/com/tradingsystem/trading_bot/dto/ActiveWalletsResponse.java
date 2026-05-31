package com.tradingsystem.trading_bot.dto;

import java.util.List;

public record ActiveWalletsResponse(
    List<ActiveWallet> activeWallets
) {
    public static ActiveWalletsResponse createActiveUsersResponse(List<ActiveWallet> activeWallets){
        return new ActiveWalletsResponse(activeWallets);
    }
}
