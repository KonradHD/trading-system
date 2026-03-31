package com.tradingsystem.trading_bot.service;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.model.MarketDataEntity;
import com.tradingsystem.trading_bot.repository.MarketDataRepository;
import com.tradingsystem.trading_bot.utils.MarketDataParser;
import com.tradingsystem.trading_bot.utils.MarketDataType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor 
public class MarketDataService {

    private final MarketDataParser parser = new MarketDataParser();
    private final MarketDataRepository marketDataRepository; 

    @Async
    public void processRawMarketData(String data, String query) {
        MarketDataType dataType = parser.checkDataType(data);

        switch (dataType) {
            case CANDLE_WEBSOCKET:
                CandleDTO candle = parser.parseWebSocketCandle(data);
                if (candle.getIsClosed()) {
                    System.out.println("New candle: " + candle.toString());
                    saveCandle(candle); 
                }
                break;

            case CANDLE_HTTP:
                List<CandleDTO> candles = parser.parseHttpCandle(data, query);
                saveAllCandles(candles); 
                break;

            case TICKER_WEBSOCKET:
                break;

            case UNKNOWN:
                System.err.println("Unknown data came from Binance API: " + data);
                break;

            default:
                throw new AssertionError();
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