package com.tradingsystem.backend.dto;

import java.math.BigDecimal;

public record CandleDTO(
        String symbol,
        BigDecimal openPrice,
        BigDecimal highPrice,
        BigDecimal lowPrice,
        BigDecimal closePrice,
        Double volume,
        Long openTime,
        Long closeTime
) {}