package com.tradingsystem.trading_bot.service;

import com.tradingsystem.trading_bot.client.BackendInternalClient;
import com.tradingsystem.trading_bot.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.repository.TransactionRepository;
import com.tradingsystem.trading_bot.utils.parsing.TransactionsParser;
import com.tradingsystem.trading_bot.dto.CommissionDTO;
import com.tradingsystem.trading_bot.dto.BinanceTransactionDTO;
import com.tradingsystem.trading_bot.model.CommissionEntity;
import com.tradingsystem.trading_bot.model.TransactionEntity;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final HttpClient client = HttpClient.newHttpClient();
    private final TransactionsParser parser = new TransactionsParser();
    private final BackendInternalClient backendClient;


    public void saveTransaction(Long walletId, BinanceTransactionDTO dto){
        TransactionEntity entity = mapToEntity(walletId, dto);
        transactionRepository.save(entity);
    }

    public void saveTransaction(TransactionEntity entity){
        transactionRepository.save(entity);
    }


    public void makeTransaction(Long walletId, HttpRequest request){
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                BinanceTransactionDTO binanceTransaction = parser.parseTransaction(response.body());
                TransactionEntity entity = mapToEntity(walletId, binanceTransaction);
                saveTransaction(entity);

                log.info("Transaction was successfully conducted and saved.");
                if(binanceTransaction.getStatus().equalsIgnoreCase("filled")){
                    log.info("Transaction was filled");
                    TransactionDTO transactionDTO = TransactionDTO.fromBinanceTransactionDTO(entity.getId(), walletId, binanceTransaction);
                    backendClient.synchronizeWallet(transactionDTO.walletId(), transactionDTO);
                }
            }else{
                log.warn("Transaction was rejected for wallet: {}.", walletId);
                log.info("Transaction response from binance: {}", response.body());
            }
        }catch(Exception e ){
            log.error("Exception occurred while making a transaction for wallet: {}, ", walletId, e);
        }
    }


    private TransactionEntity mapToEntity(Long walletId, BinanceTransactionDTO dto){
        TransactionEntity transaction = TransactionEntity.builder()
                .walletId(walletId)
                .symbol(dto.getSymbol())
                .action(dto.getAction())
                .orderId(dto.getOrderId())
                .status(dto.getStatus())
                .timestamp(dto.getTimestamp())
                .quantity(dto.getQuantity())
                .priceQty(dto.getPriceQty())
                .type(dto.getType())
                .build();

        List<CommissionDTO> safeCommissions = dto.getCommissions() != null 
                ? dto.getCommissions() 
                : Collections.emptyList();

        List<CommissionEntity> commissionEntities = safeCommissions.stream()
                .map(commissionDTO -> CommissionEntity.builder()
                        .commissionAsset(commissionDTO.getCommissionAsset())
                        .commissionValue(commissionDTO.getCommissionValue())
                        .transaction(transaction)
                        .build())
                .toList();

        transaction.setCommissions(commissionEntities);

        return transaction;
    }
}
