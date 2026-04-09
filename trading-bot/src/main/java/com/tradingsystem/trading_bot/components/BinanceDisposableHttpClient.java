package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tradingsystem.trading_bot.service.MarketDataService;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BinanceDisposableHttpClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final MarketDataService marketDataService;

    public void historicalCandles(URI url){
        System.out.println("Connecting to historical data using http...");
        HttpRequest request = HttpRequest.newBuilder()
            .uri(url)
            .GET()
            .build();

        try {
            String query = url.getQuery();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
            marketDataService.processRawMarketData(response.body(), query);

        } catch (Exception e) {
            System.out.println("Error while receiving candles data: " + e.toString());
        }
    }

}
