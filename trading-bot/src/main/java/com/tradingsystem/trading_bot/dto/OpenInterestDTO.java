package com.tradingsystem.trading_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenInterestDTO {
    
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("openInterest")
    private Double openInterest;

    @JsonProperty("time")
    private Long timestamp;

}
