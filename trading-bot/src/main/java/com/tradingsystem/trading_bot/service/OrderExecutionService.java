package com.tradingsystem.trading_bot.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

import com.tradingsystem.trading_bot.client.BackendInternalClient;
import com.tradingsystem.trading_bot.dto.InternalKeysRequest;
import com.tradingsystem.trading_bot.dto.WalletKeysDTO;
import com.tradingsystem.trading_bot.dto.WalletKeysResponse;
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
    private final TransactionService transactionService;
    private final SignatureGenerator signatureGenerator;
    private final BackendInternalClient backendClient;
    private final ActiveWalletsRegistry activeWalletsRegistry;
    private final String baseBinanceUrl;
    private final String orderPath;
    private final String balancePath;


    public OrderExecutionService(
        TransactionService transactionService,
        ActiveWalletsRegistry activeWalletsRegistry,
        BackendInternalClient backendClient,
        SignatureGenerator signatureGenerator,
        @Value("${binance.test.base.url}") String baseUrl,
        @Value("${binance.path.order}") String orderPath,
        @Value("${binance.path.balance}") String balancePath
    ){
        this.signatureGenerator = signatureGenerator;
        this.activeWalletsRegistry = activeWalletsRegistry;
        this.backendClient = backendClient;
        this.baseBinanceUrl = baseUrl;
        this.orderPath = orderPath;
        this.balancePath = balancePath;
        this.transactionService = transactionService;
    }
    
    @Async
    @EventListener
    public void tradeExecution(TradeSignalEvent signal){
        MarketActions action = signal.getAction();
        log.info("Received {} signal from trading-bot algorithm.", action);

        String parametersString = String.format("symbol=%s&side=%s&type=MARKET&quantity=%s&timestamp=%d",
                signal.getCryptoName(),
                action.toString(),
                signal.getQuantity().toPlainString(),
                Instant.now().toEpochMilli());

        List<Long> walletsId = activeWalletsRegistry.getAllActiveWallets();
        WalletKeysResponse walletKeysResponse = backendClient.fetchWalletsKeys(InternalKeysRequest.of(walletsId));

        for(WalletKeysDTO dto : walletKeysResponse.keys()){
            String signature = signatureGenerator.generate(parametersString, dto.secretKey());

            URI finalUrl = URI.create(String.format("%s%s?%s&signature=%s", baseBinanceUrl, orderPath, parametersString, signature));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(finalUrl)
                    .header("X-MBX-APIKEY", dto.apiKey())
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            transactionService.makeTransaction(dto.walletId(), request);
        }
        
    }
    
    public void checkBalance(WalletKeysDTO wallet){
        log.info("Checking account balance.");
        String rawParameters = "timestamp=" + Instant.now().toEpochMilli();

        String signature = signatureGenerator.generate(rawParameters, wallet.secretKey());
        String queryString = String.format("%s%s?%s&signature=%s", baseBinanceUrl, balancePath, rawParameters, signature);

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(queryString))
                                .headers("X-MBX-APIKEY", wallet.apiKey())
                                .GET()
                                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // TODO: Zapisać account balance 
            if(response.statusCode() == 200){
               log.info("Authorized connection! Received balance data: {}", response.body());
            } else {
                log.error("Unauthorized http connection while checking account balance. HTTP code: {}", response.statusCode());
                log.error("Error response: {}", response.body());
            }
        } catch (Exception e) {
            log.error("Exception occurred during balance checking: ", e);
        }
    }
}
