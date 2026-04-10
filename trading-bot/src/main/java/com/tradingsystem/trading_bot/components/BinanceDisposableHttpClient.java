package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.tradingsystem.trading_bot.service.MarketDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinanceDisposableHttpClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final MarketDataService marketDataService;

    public void historicalCandles(URI url){
        log.info("Connecting to historical data using http...");
        HttpRequest request = HttpRequest.newBuilder()
            .uri(url)
            .GET()
            .build();

        try {
            String query = url.getQuery();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
            marketDataService.processRawMarketData(response.body(), query);

        } catch (Exception e) {
            log.error("Error while receiving candles data: " + e.toString());
        }
    }

}
