package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.event.TradeSignalEvent;
import com.tradingsystem.trading_bot.utils.MarketActions;
import com.tradingsystem.trading_bot.utils.SignatureGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service    
public class OrderExecutionService {
    
    private final HttpClient client = HttpClient.newHttpClient();
    private final SignatureGenerator signatureGenerator;
    private final String baseBinanceUrl;
    private final String orderPath;
    private final String balancePath;
    private final String apiKey;


    public OrderExecutionService(
        @Qualifier("hmacSha256Generator") SignatureGenerator signatureGenerator,
        @Value("${binance.test.base.url}") String baseUrl,
        @Value("${binance.path.order}") String orderPath,
        @Value("${binance.path.balance}") String balancePath,
        @Value("${binance.api.key}") String apiKey
    ){
        this.signatureGenerator = signatureGenerator;
        this.baseBinanceUrl = baseUrl;
        this.orderPath = orderPath;
        this.balancePath = balancePath;
        this.apiKey = apiKey;
    }
    
    @Async
    @EventListener
    public void tradeExecution(TradeSignalEvent signal){
        MarketActions action = signal.getAction();
        log.info("Sending {} signal to BINANCE.", action);

        String parametersString = String.format("symbol=%s&side=%s&type=MARKET&quantity=%s&timestamp=%d",
                                                    signal.getCryptoName(),
                                                    action.toString(),
                                                    signal.getQuantity().toPlainString(),
                                                    Instant.now().toEpochMilli());
        String signature = signatureGenerator.generate(parametersString);

        URI finalUrl = URI.create(String.format("%s%s?%s&signature=%s", baseBinanceUrl, orderPath, parametersString, signature));
        HttpRequest request = HttpRequest.newBuilder()
            .uri(finalUrl)
            .header("X-MBX-APIKEY", apiKey)
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // TODO: Zapisać transakcje do bazy
            log.info("Transaction response from binance: " + response.body());
        }catch(Exception e ){
            e.printStackTrace();
        }
        
    }
    
    public void checkBalance(){
        log.info("Checking account balance.");
        String rawParameters = "timestamp=" + Instant.now().toEpochMilli();

        String signature = signatureGenerator.generate(rawParameters);
        String queryString = String.format("%s%s?%s&signature=%s", baseBinanceUrl, balancePath, rawParameters, signature);

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(queryString))
                                .headers("X-MBX-APIKEY", apiKey)
                                .GET()
                                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // TODO: Zapisać account balance 
            if(response.statusCode() == 200){
               log.info("Authorized connection! Received balance data: {}" + response.body());
            } else {
                log.error("Unathorized http connection while checking account balance. HTTP code: {}" + response.statusCode());
                log.error("Error response: {}" + response.body());
            }
        } catch (Exception e) {
            log.error("Exception occurred during balance checking", e);
        }
    }
}
