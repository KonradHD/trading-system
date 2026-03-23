package com.tradingsystem.trading_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CandleDTOWrapper {
    
    @JsonProperty("e")
    private String dataType;

    @JsonProperty("E")
    private long epochTime; 

    @JsonProperty("k")
    private CandleDTO candle;

}
