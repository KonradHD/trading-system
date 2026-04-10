package com.tradingsystem.trading_bot.utils.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;


public class AbstractParser {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    
    protected String extractQueryParam(String query, String paramName) {
        String searchFor = paramName + "=";
        int startIndex = query.indexOf(searchFor);
        
        if (startIndex == -1) {
            return "UNKNOWN";
        }
        
        startIndex += searchFor.length();
        
        int endIndex = query.indexOf("&", startIndex);
        if (endIndex == -1) {
            endIndex = query.length();
        }
        
        return query.substring(startIndex, endIndex);
    }

}
