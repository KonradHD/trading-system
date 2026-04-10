package com.tradingsystem.trading_bot.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.dto.FundingRateDTO;
import com.tradingsystem.trading_bot.dto.OpenInterestDTO;
import com.tradingsystem.trading_bot.utils.MarketDataType;
import com.tradingsystem.trading_bot.utils.MarketDataResolver;
import com.tradingsystem.trading_bot.utils.parsing.CandleParser;
import com.tradingsystem.trading_bot.utils.parsing.FuturesMarketParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class MarketDataRouter {

    private final CandleParser candleParser = new CandleParser();
    private final FuturesMarketParser futuresParser = new FuturesMarketParser();
    private final FuturesMarketService futuresMarketService;
    private final CandleDataService candleDataService;

    @Async
    public void processRawMarketData(String data, String query) {
        MarketDataType dataType = MarketDataResolver.checkDataType(data);

        switch (dataType) {
            case CANDLE_WEBSOCKET -> {
                CandleDTO candle = candleParser.parseWebSocketCandle(data);
                if (candle.getIsClosed()) {
                    log.info("New websocket candle was received.");
                    candleDataService.saveCandle(candle); 
                }
            }

            case CANDLE_HTTP -> {
                List<CandleDTO> candles = candleParser.parseHttpCandle(data, query);
                log.info("New candle list was received.");
                candleDataService.saveAllCandles(candles);
            }

            case OPEN_INTEREST -> {
                log.info("New open interest was received.");
                OpenInterestDTO openInsterestDTO = futuresParser.parseOpenInterest(data);
                futuresMarketService.saveOpenInterest(openInsterestDTO);
            }

            case FUNDING_RATE -> {
                log.info("New funding rate was received.");
                List<FundingRateDTO> fundingRateDTOs = futuresParser.parseFundingRate(data);
                futuresMarketService.saveAllFundingRates(fundingRateDTOs);
            }
                        
            case TICKER_WEBSOCKET -> {
                log.info("New websocket ticker was received.");
                // to się raczej nie przyda
            }

            case UNKNOWN -> log.warn("Unknown data came from Binance API: " + data);

            default -> log.error("None of data type suits icoming data.");
        }
    }

}