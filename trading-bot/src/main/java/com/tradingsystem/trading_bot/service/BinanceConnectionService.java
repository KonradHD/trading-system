package com.tradingsystem.trading_bot.service;

import java.net.URI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.config.BinanceAPIUrlBuilder;
import com.tradingsystem.trading_bot.config.BinanceWebSocketClient;

@Service
public class BinanceConnectionService implements CommandLineRunner{
    private final BinanceWebSocketClient client = new BinanceWebSocketClient();

    @Override
    public void run(String... args){
        System.out.println("Binance connection service has been starting...");
        URI url = BinanceAPIUrlBuilder.rawStreamsEndpoint("btcusdt@trade");

        client.connect(url);
    }
}