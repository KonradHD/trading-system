package com.tradingsystem.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class EncryptionService {

    @Value("${app.security.binance.password}")
    private String masterPassword;

    @Value("${app.security.binance.salt}")
    private String salt;

    private TextEncryptor encryptor;


    @PostConstruct
    public void init() {
        this.encryptor = Encryptors.text(masterPassword, salt);
    }

    public String encrypt(String rawSecretKey) {
        if (rawSecretKey == null) return null;
        return encryptor.encrypt(rawSecretKey);
    }

    public String decrypt(String encryptedSecretKey) {
        if (encryptedSecretKey == null) return null;
        return encryptor.decrypt(encryptedSecretKey);
    }
}