package com.tradingsystem.trading_bot.repository;

import com.tradingsystem.trading_bot.model.CandleDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CandleDataRepository extends JpaRepository<CandleDataEntity, Long> {
    @Query(value = "SELECT * FROM candle_data WHERE symbol = ?1 AND is_closed = true ORDER BY close_time DESC LIMIT ?2", nativeQuery = true)
    List<CandleDataEntity> findRecentCandles(String symbol, int limit);
}