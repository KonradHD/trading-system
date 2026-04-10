package com.tradingsystem.trading_bot.utils.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.tradingsystem.trading_bot.dto.TransactionDTO;

import jakarta.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionsParser extends AbstractParser {
    
    public TransactionDTO parseTransaction(String content){
        try {
            TransactionDTO transaction = objectMapper.readValue(content, TransactionDTO.class);
            return transaction; 
        } catch (Exception e) {
            log.error("Exception occured while parsing transaction: ", e);
            return null;
        }
    }
}
