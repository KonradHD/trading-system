package com.tradingsystem.trading_bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.ActiveUser;
import com.tradingsystem.trading_bot.dto.BotActiveUsersMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisListenerService {

    private final ObjectMapper objectMapper;
    private final ActiveUsersRegistry activeUsersRegistry;

    public void handleMessage(String messageJson) {
        try {
            log.debug("Received new message from Redis: {}", messageJson);

            BotActiveUsersMessage message = objectMapper.readValue(messageJson, BotActiveUsersMessage.class);

            switch (message.action()) {
                case "START" -> {
                    ActiveUser user = new ActiveUser(message.userId(), message.context());
                    activeUsersRegistry.addUser(user);
                    log.info("Successfully added user ID: {} to the bot's in-memory registry", message.userId());
                }
                case "STOP" -> {
                    activeUsersRegistry.removeUser(message.userId());
                    log.info("Stopped the bot and removed user ID: {} from the in-memory registry", message.userId());
                }
                default -> log.warn("Unknown control action received: {}", message.action());
            }

        } catch (Exception e) {
            log.error("Error while processing message from Redis", e);
        }
    }
}