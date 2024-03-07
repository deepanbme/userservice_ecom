package com.ecommerce.userservice.exception;

public class SessionLimitReachedException extends RuntimeException{

    public SessionLimitReachedException(String message) {
        super(message);
    }
}
