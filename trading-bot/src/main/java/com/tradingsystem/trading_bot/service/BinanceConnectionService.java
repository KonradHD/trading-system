package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.components.BinanceHttpClient;
import com.tradingsystem.trading_bot.components.BinanceWebSocketClient;
import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;

@Service
public class BinanceConnectionService implements CommandLineRunner{
    private final BinanceWebSocketClient socketClient = new BinanceWebSocketClient();
    private final BinanceHttpClient httpClient = new BinanceHttpClient();

    @Override
    public void run(String... args){
        System.out.println("Binance connection service is starting...");
        // URI url = BinanceAPIUrlBuilder.rawStreamsEndpoint("btcusdt");
        // URI url = BinanceAPIUrlBuilder.combinedStreamsEndpoint("btcusdt", "ethusdt");
        URI url = BinanceAPIUrlBuilder.klinesStreamEndpoint("btcusdt", "1m");
        URI url2 = BinanceAPIUrlBuilder.httpEndpoint("btcusdt", "1m", 100);
        // socketClient.connect(url);
        List<CandleDTO> candles = httpClient.historicalCandles(url2);
        System.out.println("Candles length: " + candles.size());
        System.out.println(candles.get(0).toString());
    }
}