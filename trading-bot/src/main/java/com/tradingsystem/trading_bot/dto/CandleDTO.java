package com.tradingsystem.trading_bot.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandleDTO {

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("t")
    private Long openTime;

    @JsonProperty("T") 
    private Long closeTime;

    @JsonProperty("o")
    private BigDecimal open;

    @JsonProperty("h")
    private BigDecimal high;

    @JsonProperty("l")
    private BigDecimal low;

    @JsonProperty("c")
    private BigDecimal close;

    @JsonProperty("i")
    private String interval;

    @JsonProperty("x")
    private Boolean isClosed;

    @JsonProperty("v")
    private Double volume;
}