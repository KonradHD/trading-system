package com.tradingsystem.trading_bot.components;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

import org.springframework.stereotype.Component;

import com.tradingsystem.trading_bot.service.MarketDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinanceWebSocketClient implements WebSocket.Listener{

    private final MarketDataService marketDataService;

    public void connect(URI url){
        log.info("Opening data stream...");
        HttpClient.newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(url, this);
    }

    @Override
    public void onOpen(WebSocket socket){
        log.info("Websocket connection established. Listening for prices...");
        socket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket socket, CharSequence data, boolean last){
        marketDataService.processRawMarketData(data.toString(), "websocket");
        socket.request(1); // asking for next message
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        log.error("Binance websocket connection error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        log.warn("Binance disconected. Reason: " + reason);
        return null;
    }
}
