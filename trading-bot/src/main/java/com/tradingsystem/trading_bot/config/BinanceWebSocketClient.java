package com.tradingsystem.trading_bot.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;


public class BinanceWebSocketClient implements WebSocket.Listener{
    // private final ObjectMapper objectMapper;

    public void connect(URI url){
        System.out.println("Opening data stream...");
        HttpClient.newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(url, this);
    }

    @Override
    public void onOpen(WebSocket socket){
        System.out.println("Connection established. Listening for prices...");
        socket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket socket, CharSequence data, boolean last){
        System.out.println("New transation: " + data);
        
        socket.request(1); // asking for next message
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("Binance connection error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("Binance disconected. Reason: " + reason);
        return null;
    }
}
