package com.tradingsystem.trading_bot.utils.parsing;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.tradingsystem.trading_bot.dto.CandleDTO;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandleParser extends AbstractParser {
    
    public CandleDTO parseWebSocketCandle(String data) {
        try {
     
            JsonNode node = objectMapper.readTree(data);
            JsonNode k = node.get("k"); 
            return CandleDTO.builder()
                    .symbol(k.get("s").asText())
                    .openTime(k.get("t").asLong())
                    .closeTime(k.get("T").asLong())
                    .open(new BigDecimal(k.get("o").asText()))
                    .high(new BigDecimal(k.get("h").asText()))
                    .low(new BigDecimal(k.get("l").asText()))
                    .close(new BigDecimal(k.get("c").asText()))
                    .interval(k.get("i").asText())
                    .isClosed(k.get("x").asBoolean())
                    .volume(k.get("v").asDouble())
                    .build();
        } catch (Exception e) {
            log.error("Error parsing WebSocket candle: " + e.getMessage());
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
                candle.setOpen(new BigDecimal(node.get(1).asText()));
                candle.setHigh(new BigDecimal(node.get(2).asText()));
                candle.setLow(new BigDecimal(node.get(3).asText()));
                candle.setClose(new BigDecimal(node.get(4).asText()));
                candle.setVolume(node.get(5).asDouble());
                candle.setCloseTime(node.get(6).asLong());
                candles.add(candle);
            }
        } catch (Exception e) {
            log.error("Error parsing HTTP candles: {}", e.getMessage());
        }
        return candles;
    }

}
