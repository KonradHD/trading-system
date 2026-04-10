package com.tradingsystem.trading_bot.utils.parsing;

import java.util.ArrayList;
import java.util.Collections;

import com.tradingsystem.trading_bot.dto.FundingRateDTO;
import com.tradingsystem.trading_bot.dto.OpenInterestDTO;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FuturesMarketParser extends AbstractParser {
    
    public OpenInterestDTO parseOpenInterest(String content){
        try{
            OpenInterestDTO dto = objectMapper.readValue(content, OpenInterestDTO.class);
            return dto;
        }catch(Exception e){
            log.error("Exception occurred while parsing open interest data: ", e);
            return null;
        }
    }

    public List<FundingRateDTO> parseFundingRate(String content){
        List<FundingRateDTO> rates = new ArrayList<>();
        try {
            JsonNode node = objectMapper.readTree(content);
            if(node.isArray()){
                for(JsonNode event : node){
                    FundingRateDTO dto = objectMapper.treeToValue(event, FundingRateDTO.class);
                    rates.add(dto);
                }
            }else{
                FundingRateDTO dto = objectMapper.treeToValue(node, FundingRateDTO.class);
                rates.add(dto);
            }
            return rates;

        } catch (Exception e) {
            log.error("Exception occurred while parsing funding rate data: ", e);
            return Collections.emptyList();
        }
    }
}
