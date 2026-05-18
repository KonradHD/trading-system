package com.tradingsystem.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingsystem.backend.dto.ActiveContext;
import com.tradingsystem.backend.dto.BotActiveUsersMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPublisherService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${redis.bot.active-users.endpoint}")
    private String ACTIVE_CHANNEL_NAME;

    public void notifyBotToStart(Long userId, ActiveContext context) {
        try {
            BotActiveUsersMessage message = new BotActiveUsersMessage("START", userId, context);
            String jsonPayload = objectMapper.writeValueAsString(message);
            stringRedisTemplate.convertAndSend(ACTIVE_CHANNEL_NAME, jsonPayload);
            log.info("Message was published on {} channel; user: {} starts using bot service", 
                     ACTIVE_CHANNEL_NAME, userId);

        } catch (JsonProcessingException e) {
            log.error("Cannot convert message to Redis: ", e);
        }
    }

    public void notifyBotToStop(Long userId, ActiveContext context) {
        try {
            BotActiveUsersMessage message = new BotActiveUsersMessage("STOP", userId, context);
            String jsonPayload = objectMapper.writeValueAsString(message);
            stringRedisTemplate.convertAndSend(ACTIVE_CHANNEL_NAME, jsonPayload);
            log.info("Message was published on {} channel; user: {} stops using bot service", 
                     ACTIVE_CHANNEL_NAME, userId);

        } catch (JsonProcessingException e) {
            log.error("Cannot convert message to Redis: ", e);
        }
    }

}