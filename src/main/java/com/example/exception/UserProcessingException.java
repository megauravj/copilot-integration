package com.example.exception;

/**
 * Custom exception thrown when user processing encounters invalid or empty data.
 */
public class UserProcessingException extends Exception {
    
    public UserProcessingException(String message) {
        super(message);
    }
    
    public UserProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
