package com.tradingsystem.trading_bot.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MarketDataResolver {

    public static Boolean checkOpenInterest(JsonNode node){
        return !node.path("openInterest").isMissingNode();
    }

    public static Boolean checkFundingRate(JsonNode node){
        return !node.path("fundingRate").isMissingNode();
    }

    public static Boolean checkCandleHttp(JsonNode node){
        if(node.isArray() && !node.isEmpty()){
            return node.get(0).isNumber();
        }
        return false;
    }

    public static Boolean checkCandleWebsocket(JsonNode node){
        JsonNode eventNodeCandle = node.path("e");
        return !eventNodeCandle.isMissingNode() && eventNodeCandle.asText().equals("kline");
    }

    public static MarketDataType checkDataType(String rawData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(rawData);
            if (node.isArray()) {
                node = node.get(0);
            }

            if(checkCandleHttp(node)){ 
                return MarketDataType.CANDLE_HTTP;
            }

            if (checkFundingRate(node)){ 
                return MarketDataType.FUNDING_RATE;
            }

            if (checkCandleWebsocket(node)) { 
                return MarketDataType.CANDLE_WEBSOCKET;
            }

            if(checkOpenInterest(node)){
                return MarketDataType.OPEN_INTEREST;
            }

            return MarketDataType.UNKNOWN;
        } catch (Exception e) {
            return MarketDataType.UNKNOWN;
        }
    }

}
