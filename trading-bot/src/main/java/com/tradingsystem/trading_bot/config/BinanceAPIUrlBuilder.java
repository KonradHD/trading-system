package com.tradingsystem.trading_bot.config;

import java.net.URI;

public class BinanceAPIUrlBuilder {

    public static final String BASE_STRING = "wss://stream.binance.com:9443";

    private BinanceAPIUrlBuilder(){}

    public static URI rawStreamsEndpoint(String streamName){
        String endpointStr = new StringBuilder(BASE_STRING).append("/ws/").append(streamName).toString();
        return URI.create(endpointStr);
    }


    public static URI combinedStreamsEndpoint(String... streamNames){
        StringBuilder builder = new StringBuilder(BASE_STRING).append("/stream?streams=");
        for (int i = 0; i < streamNames.length; i++){
            builder.append(streamNames[i]);
            if (i < streamNames.length - 1){
                builder.append("/");
            }
        }
        return URI.create(builder.toString());
    }
}
