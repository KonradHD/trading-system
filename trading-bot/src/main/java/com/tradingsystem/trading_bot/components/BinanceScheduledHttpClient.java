package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tradingsystem.trading_bot.service.MarketDataService;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BinanceScheduledHttpClient{
    private final HttpClient client = HttpClient.newHttpClient();

    private final MarketDataService marketDataService;
    private final String dataType;
    private final List<String> symbols;


    @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void cyclicData(){
        for(String symbol : symbols){
            URI url = BinanceAPIUrlBuilder.httpFuturesEndpoint(dataType, symbol);
            executeRequest(url);
        }
    }

    private void executeRequest(URI url){
        System.out.println("From executeRequest: " + url);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(url)
            .GET()
            .build();

        try{
            String query = url.getQuery();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            marketDataService.processRawMarketData(response.body(), query);
        }catch(Exception e){
            System.out.println("Error while receiving futures data: " + e.toString());
        }
    }
}