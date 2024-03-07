package com.ecommerce.userservice.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
