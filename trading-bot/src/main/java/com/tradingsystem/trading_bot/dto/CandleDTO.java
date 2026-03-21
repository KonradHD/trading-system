package com.tradingsystem.trading_bot.dto;

import lombok.Data;

@Data
public class CandleDTO {

    private String symbol;
    private Long openTime;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    
}
