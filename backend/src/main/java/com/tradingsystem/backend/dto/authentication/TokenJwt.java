package com.tradingsystem.backend.dto.authentication;

import java.util.Date;

public record TokenJwt (
    String value,
    Long expiresAt
){  
    public static TokenJwt createTokenJwt(String value, Date date){
        return new TokenJwt(value, date.getTime());
    }
}
