package com.example.bookManagement.exception;

public class DuplicateDataFoundException extends RuntimeException {
    public DuplicateDataFoundException(String message) {
        super(message);
    }
}
