package com.tradingsystem.trading_bot.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignatureGenerator {
    
    private final String apiSecret;
    private final String hashAlgorithm;


    public String generate(String text){
        try {
                
            // obiekt klucza
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                apiSecret.getBytes(StandardCharsets.UTF_8), 
                hashAlgorithm
            );

            // inicjalizacja maszyny szyfrującej
            Mac hashMachine = Mac.getInstance(hashAlgorithm);
            hashMachine.init(secretKeySpec);
                
            // szyfrowanie 
            byte[] rawHmac = hashMachine.doFinal(text.getBytes(StandardCharsets.UTF_8));
            
            return bytesToHex(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Cryptografic issue while generating signature.", e);
        }
    }

    private String bytesToHex(byte[] data){
        StringBuilder stringBuilder = new StringBuilder();

        for(byte b : data){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1){
                stringBuilder.append("0");
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();

    }
}
