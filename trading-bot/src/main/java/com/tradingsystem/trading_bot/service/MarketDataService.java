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
                    System.out.println("New candle: " + candle.toString());
                    saveCandle(candle); 
                }
            }

            case CANDLE_HTTP -> {
                List<CandleDTO> candles = parser.parseHttpCandle(data, query);
                System.out.println("Candles:" + candles);
                saveAllCandles(candles);
            }

            case OPEN_INTEREST -> System.out.println("Openinterest: " + data); // zapis do tabeli open_interest
                    
            case FUNDING_RATE -> System.out.println("Funding rate:" + data); // zapis do tabeli funding_rate
                        
            case TICKER_WEBSOCKET -> System.out.println("Ticker websocket: " + data);

            case UNKNOWN -> System.err.println("Unknown data came from Binance API: " + data);

            default -> throw new AssertionError();
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