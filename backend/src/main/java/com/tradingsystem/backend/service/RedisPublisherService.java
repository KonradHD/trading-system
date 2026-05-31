package com.tradingsystem.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingsystem.backend.dto.ActiveContext;
import com.tradingsystem.backend.dto.BotActiveWalletsMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPublisherService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${redis.bot.active-wallets.endpoint}")
    private String ACTIVE_CHANNEL_NAME;

    public void notifyBotToStart(Long walletId, ActiveContext context) {
        try {
            BotActiveWalletsMessage message = new BotActiveWalletsMessage("START", walletId, context);
            String jsonPayload = objectMapper.writeValueAsString(message);
            stringRedisTemplate.convertAndSend(ACTIVE_CHANNEL_NAME, jsonPayload);
            log.info("Message was published on {} channel; wallet: {} starts using bot service",
                     ACTIVE_CHANNEL_NAME, walletId);

        } catch (JsonProcessingException e) {
            log.error("Cannot convert message to Redis: ", e);
        }
    }

    public void notifyBotToStop(Long walletId, ActiveContext context) {
        try {
            BotActiveWalletsMessage message = new BotActiveWalletsMessage("STOP", walletId, context);
            String jsonPayload = objectMapper.writeValueAsString(message);
            stringRedisTemplate.convertAndSend(ACTIVE_CHANNEL_NAME, jsonPayload);
            log.info("Message was published on {} channel; wallet: {} stops using bot service",
                     ACTIVE_CHANNEL_NAME, walletId);

        } catch (JsonProcessingException e) {
            log.error("Cannot convert message to Redis: ", e);
        }
    }

}