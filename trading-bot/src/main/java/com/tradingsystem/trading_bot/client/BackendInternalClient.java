package com.tradingsystem.trading_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tradingsystem.trading_bot.config.BackendInternalConfig;
import com.tradingsystem.trading_bot.dto.ActiveUsersResponse;
import com.tradingsystem.trading_bot.dto.InternalKeysRequest;
import com.tradingsystem.trading_bot.dto.UserKeysResponse;

@FeignClient(name = "backend-service", url="${app.backend-service.url}", configuration=BackendInternalConfig.class)
public interface BackendInternalClient {
    
    @PostMapping(value="/users/keys")
    UserKeysResponse fetchUsersKeys(@RequestBody InternalKeysRequest request);

    @GetMapping(value = "/bot/active")
    ActiveUsersResponse fetchAllActiveSubscribers();

}
