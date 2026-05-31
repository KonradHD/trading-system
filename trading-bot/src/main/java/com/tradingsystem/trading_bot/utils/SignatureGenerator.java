package com.tradingsystem.trading_bot.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignatureGenerator {

    @Value("${app.security.hash-algorithm}")
    private String hashAlgorithm;

    public String generate(String text, String apiSecret){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                apiSecret.getBytes(StandardCharsets.UTF_8),
                    hashAlgorithm
            );

            Mac hashMachine = Mac.getInstance(hashAlgorithm);
            hashMachine.init(secretKeySpec);
            byte[] rawHmac = hashMachine.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Cryptographic issue while generating signature.", e);
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
