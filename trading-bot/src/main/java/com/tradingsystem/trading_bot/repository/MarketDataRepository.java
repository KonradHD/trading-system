package com.tradingsystem.trading_bot.repository;

import com.tradingsystem.trading_bot.model.MarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketDataEntity, Long> {
    @Query(value = "SELECT * FROM market_data WHERE symbol = ?1 AND is_closed = true ORDER BY close_time DESC LIMIT ?2", nativeQuery = true)
    List<MarketDataEntity> findRecentCandles(String symbol, int limit);
}