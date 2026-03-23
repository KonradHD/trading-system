package com.tradingsystem.trading_bot.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.utils.MarketDataParser;
import com.tradingsystem.trading_bot.utils.MarketDataType;

@Service
public class MarketDataService {
    private final MarketDataParser parser = new MarketDataParser();

    @Async
    public void processRawMarketData(String data, String query){
        MarketDataType dataType = parser.checkDataType(data);
        // System.out.println("marketdataService thread: " + Thread.currentThread().getName());

        switch (dataType) {
            case MarketDataType.CANDLE_WEBSOCKET:
                CandleDTO candle = parser.parseWebSocketCandle(data);
                if(candle.getIsClosed()){
                    System.out.println("New candle: " + candle.toString());
                    // TODO: send to analyzer and save in database
                }else{
                    // TODO: maybe send to analyzer
                }
                break;

            case MarketDataType.CANDLE_HTTP:
                List<CandleDTO> candles = parser.parseHttpCandle(data, query);
                System.out.println("Candles length: " + candles.size());
                System.out.println(candles.get(0).toString());
                // TODO: send to analyzer and save in database
                break;

            case MarketDataType.TICKER_WEBSOCKET:
                // TODO
                break;

            case MarketDataType.UNKNOWN:
                System.err.println("Unkown data came from Binance API: " + data);
                break;

            default:
                throw new AssertionError();
        }
    }
}
