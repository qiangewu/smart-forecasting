package com.touchspring.smartforecasting.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        this(message, null);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
