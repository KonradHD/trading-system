package com.tradingsystem.backend.exception;

public class NotEnoughResourcesException extends RuntimeException{
    public NotEnoughResourcesException(String message){
        super(message);
    }

    public static NotEnoughResourcesException createNotEnoughResourcesException(String message){
        return new NotEnoughResourcesException("Not enough existing resources: %s".formatted(message));
    }
}
