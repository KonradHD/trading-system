package com.tradingsystem.backend.dto;

import java.math.BigDecimal;

public record AnalyticsDTO(
        long totalTrades,
        long buyTrades,
        long sellTrades,
        BigDecimal totalVolume,
        BigDecimal averagePrice
) {}