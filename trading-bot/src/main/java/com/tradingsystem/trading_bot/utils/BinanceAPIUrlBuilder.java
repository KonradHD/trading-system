package com.tradingsystem.trading_bot.utils;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BinanceAPIUrlBuilder {

    private static String BINANCE_BASE_URL; 
    private static String STREAM_STRING;
    private static String CANDLE_PATH;
    private static String HTTP_FUTURES_STRING;

    @Value("${binance.test.base.url}")
    public void setBaseUrl(String value){
        BINANCE_BASE_URL = value;
    }

    @Value("${binance.websocket.stream.url}")
    public void setStreamString(String value){
        STREAM_STRING = value;
    }

    @Value("${binance.path.klines}")
    public void setHttpString(String value){
        CANDLE_PATH = value;
    }

    @Value("${binance.http.futures.url}")
    public void setHttpFuturesString(String value){
        HTTP_FUTURES_STRING = value;
    }

    public static URI rawStreamEndpoint(String streamName){
        String endpointStr = new StringBuilder(STREAM_STRING).append("/ws/").append(streamName).append("@trade").toString();
        return URI.create(endpointStr);
    }


    public static URI combinedStreamsEndpoint(String... streamNames){
        StringBuilder builder = new StringBuilder(STREAM_STRING).append("/stream?streams=");
        for (int i = 0; i < streamNames.length; i++){
            builder.append(streamNames[i]).append("@trade");
            if (i < streamNames.length - 1){
                builder.append("/");
            }
        }
        return URI.create(builder.toString());
    }

    public static URI streamCandleEndpoint(String streamName, String interval){
        String urlString = String.format("%s/ws/%s@kline_%s", STREAM_STRING, streamName, interval);
        return URI.create(urlString);
    }

    public static URI httpCandleEndpoint(String symbol, String interval, int limit){
        String urlString = String.format("%s%s?symbol=%s&interval=%s&limit=%d", BINANCE_BASE_URL, CANDLE_PATH, symbol.toUpperCase(), interval, limit);
        return URI.create(urlString);
    }

    public static URI httpFuturesEndpoint(String type, String symbol){
        String urlString = String.format("%s%s?symbol=%s", HTTP_FUTURES_STRING, type, symbol.toUpperCase());
        return URI.create(urlString);
    }
}
