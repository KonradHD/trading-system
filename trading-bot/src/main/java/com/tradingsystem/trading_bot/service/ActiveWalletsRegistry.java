package com.tradingsystem.trading_bot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tradingsystem.trading_bot.dto.ActiveWallet;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.ActiveContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActiveWalletsRegistry {

    private final Map<Long, ActiveContext> activeWallets = new ConcurrentHashMap<>();

    public void addWallet(ActiveWallet wallet) {
        if (activeWallets.putIfAbsent(wallet.userId(), wallet.context()) == null) {
            log.info("Wallet {} is connected to trading-bot.", wallet.userId());
        } else {
            log.warn("Ignored START command: Wallet {} is already connected.", wallet.userId());
        }
    }

    public void removeWallet(Long walletId) {
        if (activeWallets.remove(walletId) != null) {
            log.info("Wallet {} is disconnected from trading-bot.", walletId);
        } else {
            log.debug("Ignored STOP command: Wallet {} was not connected.", walletId);
        }
    }

    public List<Long> getAllActiveWallets() {
        return new ArrayList<>(activeWallets.keySet());
    }
    
    public boolean isActive(Long walletId) {
        return activeWallets.containsKey(walletId);
    }
}