package com.tradingsystem.backend.exception;


public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException userAlreadyExistsException() {
        return new UserAlreadyExistsException("User already exists with given username or email");
    }

}
