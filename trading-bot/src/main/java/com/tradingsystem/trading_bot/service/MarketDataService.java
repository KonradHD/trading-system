package com.tradingsystem.trading_bot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.model.MarketDataEntity;
import com.tradingsystem.trading_bot.repository.MarketDataRepository;
import com.tradingsystem.trading_bot.utils.MarketDataParser;
import com.tradingsystem.trading_bot.utils.MarketDataType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class MarketDataService {

    private final MarketDataParser parser = new MarketDataParser();
    private final MarketDataRepository marketDataRepository; 

    @Async
    public void processRawMarketData(String data, String query) {
        MarketDataType dataType = parser.checkDataType(data);

        switch (dataType) {
            case CANDLE_WEBSOCKET -> {
                CandleDTO candle = parser.parseWebSocketCandle(data);
                if (candle.getIsClosed()) {
                    log.info("New websocket candle was received.");
                    saveCandle(candle); 
                }
            }

            case CANDLE_HTTP -> {
                List<CandleDTO> candles = parser.parseHttpCandle(data, query);
                log.info("New candle list was received.");
                saveAllCandles(candles);
            }

            case OPEN_INTEREST -> {
                log.info("New open interest was received.");
                // TODO: zapis do tabeli open_interest
            }

            case FUNDING_RATE -> {
                log.info("New funding rate was received.");
                // TODO: zapis do tabeli funding_rate
            }
                        
            case TICKER_WEBSOCKET -> {
                log.info("New websocket ticker was received.");
                // to się raczej nie przyda
            }

            case UNKNOWN -> log.warn("Unknown data came from Binance API: " + data);

            default -> log.error("None of data type suits icoming data.");
        }
    }

    private void saveCandle(CandleDTO dto) {
        MarketDataEntity entity = mapToEntity(dto);
        marketDataRepository.save(entity);
    }

    private void saveAllCandles(List<CandleDTO> dtos) {
        List<MarketDataEntity> entities = dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        marketDataRepository.saveAll(entities);
    }

    private MarketDataEntity mapToEntity(CandleDTO dto) {
        return MarketDataEntity.builder()
                .symbol(dto.getSymbol())
                .openPrice(dto.getOpen())
                .highPrice(dto.getHigh())
                .lowPrice(dto.getLow())
                .closePrice(dto.getClose())
                .volume(dto.getVolume())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .isClosed(dto.getIsClosed())
                .build();
    }
}