package com.tradingsystem.trading_bot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.ActiveContext;
import com.tradingsystem.trading_bot.dto.ActiveUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActiveUsersRegistry {

    private final Map<Long, ActiveContext> activeUsers = new ConcurrentHashMap<>();

    public void addUser(ActiveUser user) {
        if (activeUsers.putIfAbsent(user.userId(), user.context()) == null) {
            log.info("User {} is connected to trading-bot.", user.userId());
        } else {
            log.warn("Ignored START command: User {} is already connected.", user.userId());
        }
    }

    public void removeUser(Long userId) {
        if (activeUsers.remove(userId) != null) {
            log.info("User {} is disconnected from trading-bot.", userId);
        } else {
            log.debug("Ignored STOP command: User {} was not connected.", userId);
        }
    }

    public List<Long> getAllActiveUsers() {
        return new ArrayList<>(activeUsers.keySet());
    }
    
    public boolean isActive(Long userId) {
        return activeUsers.containsKey(userId);
    }
}