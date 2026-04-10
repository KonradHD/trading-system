package com.tradingsystem.trading_bot.event;

import java.math.BigDecimal;
import java.time.Instant;

import com.tradingsystem.trading_bot.utils.MarketActions;

import lombok.Data;

@Data
public class TradeSignalEvent {
    private MarketActions action;
    private String cryptoName;
    private BigDecimal quantity;
    private String reason;
    private Instant creationTime;

    public TradeSignalEvent(MarketActions action, String cryptoName, BigDecimal quantity, String reason, Instant creationTime){
        this.action = action;
        this.cryptoName = cryptoName.toUpperCase();
        this.reason = reason;
        this.quantity = quantity;
        this.creationTime = creationTime;
    }
}
