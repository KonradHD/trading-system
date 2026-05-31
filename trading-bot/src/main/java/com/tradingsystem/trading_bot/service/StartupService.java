package com.tradingsystem.trading_bot.service;

import com.tradingsystem.trading_bot.dto.ActiveWallet;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import com.tradingsystem.trading_bot.client.BackendInternalClient;
import com.tradingsystem.trading_bot.dto.ActiveWalletsResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StartupService {

    private final BackendInternalClient client;
    private final ActiveWalletsRegistry registry;

    public StartupService(BackendInternalClient client, ActiveWalletsRegistry registry) {
        this.client = client;
        this.registry = registry;
    }

    @Retryable(backoff = @Backoff(delay = 2000))
    public void initializeActiveUsers() {
        log.info("Fetching active subscribers from Backend...");
        ActiveWalletsResponse activeWallets = client.fetchAllActiveSubscribers();
        activeWallets.activeWallets().forEach(registry::addWallet);
        log.info("Successfully fetched {} subscribed wallets.", activeWallets.activeWallets().size());
    }
}
