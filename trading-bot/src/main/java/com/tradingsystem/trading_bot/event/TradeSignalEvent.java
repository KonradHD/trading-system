package com.tradingsystem.trading_bot.event;

import com.tradingsystem.trading_bot.utils.MarketActions;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class TradeSignalEvent {
    private MarketActions action;
    private String cryptoName;
    private BigDecimal amount;
    private String reason;
    private Instant creationTime;
}
