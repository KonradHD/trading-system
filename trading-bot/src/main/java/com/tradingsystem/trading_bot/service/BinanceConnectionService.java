package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
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
    
    @Qualifier("openInterestClient")
    private final BinanceScheduledHttpClient openInterestClient;
    
    @Qualifier("fundingRateClient")
    private final BinanceScheduledHttpClient fundingRateClient;
    
    @Override
    public void run(String... args){
        System.out.println("Binance connection service is starting...");
        // URI url = BinanceAPIUrlBuilder.rawStreamsEndpoint("btcusdt");
        // URI url = BinanceAPIUrlBuilder.combinedStreamsEndpoint("btcusdt", "ethusdt");
        URI url = BinanceAPIUrlBuilder.klinesStreamEndpoint("btcusdt", "1m");
        URI url2 = BinanceAPIUrlBuilder.httpEndpoint("btcusdt", "1m", 100);
        socketClient.connect(url);
        // disposableHttpClient.historicalCandles(url2);
        
        openInterestClient.cyclicData();
    }
}