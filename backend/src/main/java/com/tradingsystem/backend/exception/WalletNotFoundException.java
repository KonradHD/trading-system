package com.tradingsystem.backend.exception;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(String message){
        super(message);
    }

    public static WalletNotFoundException createWalletNotFoundException(Long walletId){
        String message = "The wallet with id: %s does not exist or is inactive.".formatted(walletId);
        return new WalletNotFoundException(message);
    }
}
