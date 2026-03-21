package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.tradingsystem.trading_bot.dto.CandleDTO;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class BinanceHttpClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CandleDTO> historicalCandles(URI url){
        System.out.println("Connecting to historical data using http...");
        HttpRequest request = HttpRequest.newBuilder()
            .uri(url)
            .GET()
            .build();
        
        List<CandleDTO> candles = new ArrayList<>();

        try {
            String query = url.getQuery();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonArray = objectMapper.readTree(response.body());
            
            for(JsonNode node : jsonArray){
                CandleDTO candle = new CandleDTO();
                candle.setSymbol(query.substring(query.indexOf("=") + 1, query.indexOf("&")));
                candle.setOpenTime(node.get(0).asLong());
                candle.setOpen(node.get(1).asDouble());
                candle.setHigh(node.get(2).asDouble());
                candle.setLow(node.get(3).asDouble());
                candle.setClose(node.get(4).asDouble());
                candle.setVolume(node.get(5).asDouble());

                candles.add(candle);
            }

        } catch (Exception e) {
            System.out.println("Error while receiving candles data: " + e.toString());
        }
        return candles;
    }
}
