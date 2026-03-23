package com.tradingsystem.trading_bot.utils;

import lombok.Getter;

public enum Timeframe {

    ONE_MINUTE("1m", 60),
    FIVE_MINUTES("5m", 300),
    ONE_HOUR("1h", 3600),
    ONE_DAY("1d", 86400);

    @Getter
    private final String binanceCode;

    @Getter
    private final int durationInSeconds;

    Timeframe(String binanceCode, int durationInSeconds) {
        this.binanceCode = binanceCode;
        this.durationInSeconds = durationInSeconds;
    }
}