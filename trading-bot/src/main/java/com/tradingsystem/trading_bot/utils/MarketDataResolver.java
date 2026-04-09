package com.tradingsystem.trading_bot.utils;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MarketDataResolver {

    public Boolean checkOpenInterest(JsonNode node){
        return !node.path("openInterest").isMissingNode();
    }

    public Boolean checkFundingRate(JsonNode node){
        return !node.path("fundingRate").isMissingNode();
    }

    public Boolean checkCandleHttp(JsonNode node){
        if(node.isArray() && !node.isEmpty()){
            return node.get(0).isNumber();
        }
        return false;
    }

    public Boolean checkCandleWebsocket(JsonNode node){
        JsonNode eventNodeCandle = node.path("e");
        return !eventNodeCandle.isMissingNode() && eventNodeCandle.asText().equals("kline");
    }

}
