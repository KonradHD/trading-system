package com.tradingsystem.trading_bot.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tradingsystem.trading_bot.components.BinanceScheduledHttpClient;
import com.tradingsystem.trading_bot.service.MarketDataService;

@Configuration
public class BinanceScheduledHttpConfig {
    
    @Bean
    public BinanceScheduledHttpClient openInterestClient(MarketDataService service){
        return new BinanceScheduledHttpClient(service, "openInterest", List.of("btcusdt"));
    }

    @Bean
    public BinanceScheduledHttpClient fundingRateClient(MarketDataService service){
        return new BinanceScheduledHttpClient(service, "fundingRate", List.of("btcusdt"));
    }
}
