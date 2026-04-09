package com.tradingsystem.trading_bot.utils;

import java.util.ArrayList;
import java.util.List;
import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarketDataParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
            for (JsonNode node : jsonArray) { // zmienić na to co było wcześniej
                CandleDTO candle = new CandleDTO();
                candle.setSymbol("BTCUSDT"); 
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
                return MarketDataType.CANDLE_HTTP;
            }

            JsonNode eventNodeInterest = node.path("openInterest");
            if (eventNodeInterest != null){ 
                return MarketDataType.OPEN_INTEREST;
            }

            JsonNode eventNodeRate = node.path("fundingRate");
            if (eventNodeRate != null){ 
                return MarketDataType.FUNDING_RATE;
            }

            JsonNode eventNodeCandle = node.path("e");
            if (eventNodeCandle != null && eventNodeCandle.asText().equals("kline")) { 
                return MarketDataType.CANDLE_WEBSOCKET;
            }
            return MarketDataType.UNKNOWN;
        } catch (Exception e) {
            return MarketDataType.UNKNOWN;
        }
    }
}