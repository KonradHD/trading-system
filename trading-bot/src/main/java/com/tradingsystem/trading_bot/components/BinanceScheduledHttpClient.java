package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.tradingsystem.trading_bot.service.MarketDataRouter;
import com.tradingsystem.trading_bot.utils.BinanceAPIUrlBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BinanceScheduledHttpClient{
    private final HttpClient client = HttpClient.newHttpClient();

    private final MarketDataRouter marketDataService;
    private final String dataType;
    private final List<String> symbols;


    // @Scheduled(fixedRateString = "${cyclic.http.time}")
    public void fetchData(){
        for(String symbol : symbols){
            URI url = BinanceAPIUrlBuilder.httpFuturesEndpoint(dataType, symbol);
            executeRequest(url);
        }
    }

    private void executeRequest(URI url){
        HttpRequest req = HttpRequest.newBuilder()
            .uri(url)
            .GET()
            .build();

        try{
            String query = url.getQuery();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            marketDataService.processRawMarketData(response.body(), query);
        }catch(Exception e){
            log.error("Error while receiving futures data: " + e.toString());
        }
    }
}