package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.components.BinanceDisposableHttpClient;
import com.tradingsystem.trading_bot.components.BinanceScheduledHttpClient;
import com.tradingsystem.trading_bot.components.BinanceWebSocketClient;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinanceConnectionService implements CommandLineRunner {

    private final BinanceWebSocketClient socketClient;
    private final BinanceDisposableHttpClient disposableHttpClient;
    private final List<BinanceScheduledHttpClient> httpClients;

    @Override
    public void run(String... args) {
        log.info("Binance connection service is starting...");

        URI websocketCandlesUrl = BinanceAPIUrlBuilder.streamCandleEndpoint("btcusdt", "1m");
        URI historicalCandlesUrl = BinanceAPIUrlBuilder.httpCandleEndpoint("btcusdt", "1m", 100);

        disposableHttpClient.historicalCandles(historicalCandlesUrl);
        socketClient.connect(websocketCandlesUrl);

        log.info("Historical candles loaded and websocket candle stream connected.");
    }

    @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void triggerHttpDataFetch() {
        log.info("Fetching scheduled data using HTTP.");

        for (BinanceScheduledHttpClient client : httpClients) {
            client.fetchData();
        }
    }
}