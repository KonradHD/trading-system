package com.tradingsystem.trading_bot.client;

import com.tradingsystem.trading_bot.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.tradingsystem.trading_bot.config.BackendInternalConfig;

@FeignClient(name = "backend-service", url="${app.backend-service.url}", configuration=BackendInternalConfig.class)
public interface BackendInternalClient {
    
    @PostMapping(value="/wallets/keys")
    WalletKeysResponse fetchWalletsKeys(@RequestBody InternalKeysRequest request);

    @GetMapping(value = "/bot/active")
    ActiveWalletsResponse fetchAllActiveSubscribers();

    @PutMapping(value = "/wallets/{wallet_id}")
    WalletSynchroResponse synchronizeWallet(
            @PathVariable("wallet_id") Long walletId,
            @RequestBody TransactionDTO transaction);

}
