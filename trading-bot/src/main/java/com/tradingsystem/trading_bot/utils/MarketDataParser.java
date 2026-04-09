package com.tradingsystem.trading_bot.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingsystem.trading_bot.dto.CandleDTO;

public class MarketDataParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MarketDataResolver resolver = new MarketDataResolver();

    public CandleDTO parseWebSocketCandle(String data) {
        try {
     
            JsonNode node = objectMapper.readTree(data);
            JsonNode k = node.get("k"); 
            return CandleDTO.builder()
                    .symbol(k.get("s").asText())
                    .openTime(k.get("t").asLong())
                    .closeTime(k.get("T").asLong())
                    .open(k.get("o").asDouble())
                    .high(k.get("h").asDouble())
                    .low(k.get("l").asDouble())
                    .close(k.get("c").asDouble())
                    .interval(k.get("i").asText())
                    .isClosed(k.get("x").asBoolean())
                    .volume(k.get("v").asDouble())
                    .build();
        } catch (Exception e) {
            System.err.println("Error parsing WebSocket candle: " + e.getMessage());
            return null;
        }
    }

    public List<CandleDTO> parseHttpCandle(String content, String query) {
        List<CandleDTO> candles = new ArrayList<>();
        try {
            JsonNode jsonArray = objectMapper.readTree(content);
            for (JsonNode node : jsonArray) {
                CandleDTO candle = new CandleDTO();
                String symbol = extractQueryParam(query, "symbol");
                String interval = extractQueryParam(query, "interval");
                
                candle.setSymbol(symbol);
                candle.setInterval(interval);
                candle.setIsClosed(true);
                candle.setOpenTime(node.get(0).asLong());
                candle.setOpen(node.get(1).asDouble());
                candle.setHigh(node.get(2).asDouble());
                candle.setLow(node.get(3).asDouble());
                candle.setClose(node.get(4).asDouble());
                candle.setVolume(node.get(5).asDouble());
                candle.setCloseTime(node.get(6).asLong());
                candles.add(candle);
            }
        } catch (Exception e) {
            System.err.println("Error parsing HTTP candles: " + e.getMessage());
        }
        return candles;
    }

    public MarketDataType checkDataType(String rawData) {
        try {
            JsonNode node = objectMapper.readTree(rawData);
            if (node.isArray()) {
                node = node.get(0);
            }

            if(resolver.checkCandleHttp(node)){ 
                return MarketDataType.CANDLE_HTTP;
            }

            if (resolver.checkFundingRate(node)){ 
                return MarketDataType.FUNDING_RATE;
            }

            if (resolver.checkCandleWebsocket(node)) { 
                return MarketDataType.CANDLE_WEBSOCKET;
            }

            if(resolver.checkOpenInterest(node)){
                return MarketDataType.OPEN_INTEREST;
            }

            return MarketDataType.UNKNOWN;
        } catch (Exception e) {
            return MarketDataType.UNKNOWN;
        }
    }

    private String extractQueryParam(String query, String paramName) {
        String searchFor = paramName + "=";
        int startIndex = query.indexOf(searchFor);
        
        if (startIndex == -1) {
            return "UNKNOWN"; // Jeśli nie ma parametru w URL
        }
        
        startIndex += searchFor.length(); // Przesuwamy indeks za znak "="
        
        int endIndex = query.indexOf("&", startIndex);
        if (endIndex == -1) {
            // Jeśli nie ma '&', to znaczy, że to ostatni parametr w stringu.
            // Bierzemy wszystko do samego końca.
            endIndex = query.length();
        }
        
        return query.substring(startIndex, endIndex);
    }
}