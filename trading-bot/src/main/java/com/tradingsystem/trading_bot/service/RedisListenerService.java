package com.tradingsystem.trading_bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.ActiveWallet;
import com.tradingsystem.trading_bot.dto.BotActiveWalletsMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisListenerService {

    private final ObjectMapper objectMapper;
    private final ActiveWalletsRegistry activeWalletsRegistry;

    public void handleMessage(String messageJson) {
        try {
            log.debug("Received new message from Redis: {}", messageJson);

            BotActiveWalletsMessage message = objectMapper.readValue(messageJson, BotActiveWalletsMessage.class);

            switch (message.action()) {
                case "START" -> {
                    ActiveWallet wallet = new ActiveWallet(message.walletId(), message.context());
                    activeWalletsRegistry.addWallet(wallet);
                    log.info("Successfully added wallet: {} to the bot's in-memory registry", message.walletId());
                }
                case "STOP" -> {
                    activeWalletsRegistry.removeWallet(message.walletId());
                    log.info("Stopped the bot and removed wallet: {} from the in-memory registry", message.walletId());
                }
                default -> log.warn("Unknown control action received: {}", message.action());
            }

        } catch (Exception e) {
            log.error("Error while processing message from Redis", e);
        }
    }
}