package com.tradingsystem.trading_bot.utils;

import java.util.ArrayList;
import java.util.List;

import com.tradingsystem.trading_bot.dto.CandleDTO;
import com.tradingsystem.trading_bot.dto.CandleDTOWrapper;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class MarketDataParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CandleDTO parseWebSocketCandle(String data){
        CandleDTOWrapper candleWrapper = objectMapper.readValue(data, CandleDTOWrapper.class);
        return candleWrapper.getCandle();
    }


    public List<CandleDTO> parseHttpCandle(String content, String query){
        List<CandleDTO> candles = new ArrayList<>();

        JsonNode jsonArray = objectMapper.readTree(content);
            
            for(JsonNode node : jsonArray){
                
                CandleDTO candle = new CandleDTO();
                int firstIndex = query.indexOf("&");
        
                candle.setSymbol(query.substring(query.indexOf("?symbol=") + 1, firstIndex));
                candle.setInterval(query.substring(query.indexOf("&interval=") + 1, query.indexOf("&", firstIndex + 1)));
                candle.setIsClosed(true);
                candle.setOpenTime(node.get(0).asLong());
                candle.setOpenPrice(node.get(1).asDouble());
                candle.setHighPrice(node.get(2).asDouble());
                candle.setLowPrice(node.get(3).asDouble());
                candle.setClosePrice(node.get(4).asDouble());
                candle.setVolume(node.get(5).asDouble());

                candles.add(candle);
            }

        return candles;
    }

    public MarketDataType checkDataType(String rawData){
        try {

            JsonNode node = objectMapper.readTree(rawData);
            if(node.isArray()){
                return MarketDataType.CANDLE_HTTP;
            }

            // zwraca MissingNode w przypadku braku pola "e"
            JsonNode eventNode = node.path("e");

            if(eventNode.asString().equals("kline")){
                return MarketDataType.CANDLE_WEBSOCKET;
            }

            return MarketDataType.UNKNOWN;
            
        } catch (Exception e) {
            System.err.println("Error while parsing json data: " + e.getMessage());
            return MarketDataType.UNKNOWN;
        }
    }
}
