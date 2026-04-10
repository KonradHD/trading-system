package com.tradingsystem.trading_bot.service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.components.BinanceDisposableHttpClient;
import com.tradingsystem.trading_bot.components.BinanceScheduledHttpClient;
import com.tradingsystem.trading_bot.components.BinanceWebSocketClient;
import com.tradingsystem.trading_bot.event.TradeSignalEvent;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;
import com.tradingsystem.trading_bot.utils.MarketActions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinanceConnectionService implements CommandLineRunner{
    private final BinanceWebSocketClient socketClient;
    private final BinanceDisposableHttpClient disposableHttpClient;
    private final List<BinanceScheduledHttpClient> httpClients;
    private final OrderExecutionService orderService;
    
    @Override
    public void run(String... args){
        log.info("Binance connection service is starting...");
        // URI url = BinanceAPIUrlBuilder.rawStreamsEndpoint("btcusdt");
        // URI url = BinanceAPIUrlBuilder.combinedStreamsEndpoint("btcusdt", "ethusdt");
        URI url = BinanceAPIUrlBuilder.streamCandleEndpoint("btcusdt", "1m");
        URI url2 = BinanceAPIUrlBuilder.httpCandleEndpoint("btcusdt", "1m", 100);
        socketClient.connect(url);
        disposableHttpClient.historicalCandles(url2);
        
        // orderService.checkBalance();

        TradeSignalEvent signal = new TradeSignalEvent(
            MarketActions.BUY,
            "btcusdt",
            BigDecimal.valueOf(0.01),
            "Test API",
            Instant.now()
        );
        // orderService.tradeExecution(signal);
    }

    @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void triggerHttpDataFetch() {
        log.info("Fetching scheduled data using HTTP.");
        for(BinanceScheduledHttpClient client : httpClients) {
            client.fetchData();
        }
    }
}