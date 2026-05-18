package com.tradingsystem.trading_bot.service;

import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import com.tradingsystem.trading_bot.client.BackendInternalClient;
import com.tradingsystem.trading_bot.dto.ActiveUsersResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StartupService {

    private final BackendInternalClient client;
    private final ActiveUsersRegistry registry;

    public StartupService(BackendInternalClient client, ActiveUsersRegistry registry) {
        this.client = client;
        this.registry = registry;
    }

    @Retryable(backoff = @Backoff(delay = 2000))
    public void initializeActiveUsers() {
        log.info("Fetching active subscribers from Backend...");
        ActiveUsersResponse activeUsers = client.fetchAllActiveSubscribers();
        activeUsers.activeUsers().forEach(registry::addUser);
        log.info("Successfully fetched {} subscribers.", activeUsers.activeUsers().size());
    }
}
