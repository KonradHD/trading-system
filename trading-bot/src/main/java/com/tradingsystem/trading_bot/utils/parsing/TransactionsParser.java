package com.tradingsystem.trading_bot.utils.parsing;

import com.tradingsystem.trading_bot.dto.BinanceTransactionDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionsParser extends AbstractParser {
    
    public BinanceTransactionDTO parseTransaction(String content){
        try {
            BinanceTransactionDTO transaction = objectMapper.readValue(content, BinanceTransactionDTO.class);
            return transaction; 
        } catch (Exception e) {
            log.error("Exception occured while parsing transaction: ", e);
            return null;
        }
    }
}
