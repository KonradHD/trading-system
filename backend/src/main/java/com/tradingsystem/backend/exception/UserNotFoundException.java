package com.tradingsystem.backend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }

    public static UserNotFoundException userNotFoundException(){
        return new UserNotFoundException("User does not exist with given login");
    }
}
