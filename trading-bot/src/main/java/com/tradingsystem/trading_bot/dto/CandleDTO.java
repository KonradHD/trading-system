package com.tradingsystem.trading_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleDTO {

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("t")
    private Long openTime;

    @JsonProperty("o")
    private Double openPrice;

    @JsonProperty("h")
    private Double highPrice;

    @JsonProperty("l")
    private Double lowPrice;

    @JsonProperty("c")
    private Double closePrice;

    @JsonProperty("i")
    private String interval;

    @JsonProperty("x")
    private Boolean isClosed;

    @JsonProperty("v")
    private Double volume;
    
}
