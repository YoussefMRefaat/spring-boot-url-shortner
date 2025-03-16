package com.youssef.urlshortener.exception;

public class UserLimitExceededException extends RuntimeException{
    public UserLimitExceededException(String message) {
        super(message);
    }
}
