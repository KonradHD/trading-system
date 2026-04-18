package com.tradingsystem.backend.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenEncrypter {

    public String encode(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Server error: No algorithm SHA-256", e);
        }
    }

    public boolean matches(String rawToken, String hashedToken) {
        if (rawToken == null || hashedToken == null) {
            return false;
        }
        return encode(rawToken).equals(hashedToken);
    }
}