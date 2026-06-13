package com.tradingsystem.backend.controller;

import com.tradingsystem.backend.dto.CandleDTO;
import com.tradingsystem.backend.dto.StockSymbolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<StockSymbolDTO> getAvailableSymbols() {
        return jdbcTemplate.query(
                """
                SELECT DISTINCT symbol
                FROM candles
                ORDER BY symbol
                """,
                (rs, rowNum) -> new StockSymbolDTO(
                        rs.getString("symbol")
                )
        );
    }

    @GetMapping("/{symbol}")
    public List<CandleDTO> getCandlesBySymbol(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "100") int limit
    ) {
        return jdbcTemplate.query(
                """
                SELECT symbol, open_price, high_price, low_price, close_price, volume, open_time, close_time
                FROM candles
                WHERE UPPER(symbol) = UPPER(?)
                ORDER BY close_time DESC
                LIMIT ?
                """,
                (rs, rowNum) -> new CandleDTO(
                        rs.getString("symbol"),
                        rs.getBigDecimal("open_price"),
                        rs.getBigDecimal("high_price"),
                        rs.getBigDecimal("low_price"),
                        rs.getBigDecimal("close_price"),
                        rs.getDouble("volume"),
                        rs.getLong("open_time"),
                        rs.getLong("close_time")
                ),
                symbol,
                limit
        );
    }
}