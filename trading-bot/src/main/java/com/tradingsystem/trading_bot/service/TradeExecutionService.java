package com.tradingsystem.trading_bot.service;

import com.tradingsystem.trading_bot.event.TradeSignalEvent;
import com.tradingsystem.trading_bot.utils.MarketActions;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.context.event.EventListener;


@Service
public class TradeExecutionService {

    @Async
    @EventListener
    public void tradeExecution(TradeSignalEvent signal){
        if(signal.getAction() == MarketActions.BUY){
            System.out.println("Sending BUY signal to BINANCE.");
            // TODO : Buy actions
        }
        else if(signal.getAction() == MarketActions.SELL){
            System.out.println("Sending SELL signal to BINANCE.");
            // TODO : Sell actions
        }
    }
    
}
