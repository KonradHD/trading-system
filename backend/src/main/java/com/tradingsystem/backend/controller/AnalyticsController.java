package com.tradingsystem.backend.controller;

import com.tradingsystem.backend.dto.AnalyticsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public AnalyticsDTO getGlobalAnalytics() {
        return jdbcTemplate.queryForObject(
                """
                SELECT
                    COUNT(*) AS total_trades,
                    COUNT(*) FILTER (WHERE action = 'BUY') AS buy_trades,
                    COUNT(*) FILTER (WHERE action = 'SELL') AS sell_trades,
                    COALESCE(SUM(quantity * price_qty), 0) AS total_volume,
                    COALESCE(AVG(price_qty), 0) AS average_price
                FROM transactions
                """,
                (rs, rowNum) -> new AnalyticsDTO(
                        rs.getLong("total_trades"),
                        rs.getLong("buy_trades"),
                        rs.getLong("sell_trades"),
                        rs.getBigDecimal("total_volume"),
                        rs.getBigDecimal("average_price")
                )
        );
    }

    @GetMapping("/wallet/{walletId}")
    public AnalyticsDTO getWalletAnalytics(@PathVariable Long walletId) {
        return jdbcTemplate.queryForObject(
                """
                SELECT
                    COUNT(*) AS total_trades,
                    COUNT(*) FILTER (WHERE action = 'BUY') AS buy_trades,
                    COUNT(*) FILTER (WHERE action = 'SELL') AS sell_trades,
                    COALESCE(SUM(quantity * price_qty), 0) AS total_volume,
                    COALESCE(AVG(price_qty), 0) AS average_price
                FROM transactions
                WHERE wallet_id = ?
                """,
                (rs, rowNum) -> new AnalyticsDTO(
                        rs.getLong("total_trades"),
                        rs.getLong("buy_trades"),
                        rs.getLong("sell_trades"),
                        rs.getBigDecimal("total_volume"),
                        rs.getBigDecimal("average_price")
                ),
                walletId
        );
    }
}