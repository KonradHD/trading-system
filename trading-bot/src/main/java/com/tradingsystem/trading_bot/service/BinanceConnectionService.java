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

@Service
@RequiredArgsConstructor
public class BinanceConnectionService implements CommandLineRunner{
    private final BinanceWebSocketClient socketClient;
    private final BinanceDisposableHttpClient disposableHttpClient;
    private final List<BinanceScheduledHttpClient> httpClients;
    
    @Override
    public void run(String... args){
        System.out.println("Binance connection service is starting...");
        // URI url = BinanceAPIUrlBuilder.rawStreamsEndpoint("btcusdt");
        // URI url = BinanceAPIUrlBuilder.combinedStreamsEndpoint("btcusdt", "ethusdt");
        URI url = BinanceAPIUrlBuilder.streamCandleEndpoint("btcusdt", "1m");
        URI url2 = BinanceAPIUrlBuilder.httpCandleEndpoint("btcusdt", "1m", 100);
        socketClient.connect(url);
        disposableHttpClient.historicalCandles(url2);
        
    }

    @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void triggerHttpDataFetch() {
        System.out.println("--- cyclic HTTP requests ---");
        for(BinanceScheduledHttpClient client : httpClients) {
            client.fetchData();
        }
    }
}