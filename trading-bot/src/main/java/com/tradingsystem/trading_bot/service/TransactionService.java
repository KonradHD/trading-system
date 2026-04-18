package com.tradingsystem.trading_bot.service;

import org.springframework.stereotype.Service;

import com.tradingsystem.trading_bot.repository.TransactionRepository;
import com.tradingsystem.trading_bot.utils.parsing.TransactionsParser;
import com.tradingsystem.trading_bot.dto.CommissionDTO;
import com.tradingsystem.trading_bot.dto.TransactionDTO;
import com.tradingsystem.trading_bot.model.CommissionEntity;
import com.tradingsystem.trading_bot.model.TransactionEntity;

import java.util.Collections;
import java.util.List;

import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void saveTransaction(TransactionDTO dto){
        TransactionEntity entity = mapToEntity(dto);
        transactionRepository.save(entity);
    }

    private TransactionEntity mapToEntity(TransactionDTO dto){
        TransactionEntity transaction = TransactionEntity.builder()
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
