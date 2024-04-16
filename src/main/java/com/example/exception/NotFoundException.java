package com.example.exception;

public class NotFoundException extends GenericException{
    public NotFoundException() {
        super("Item not found in database");
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
