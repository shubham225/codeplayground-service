package com.shubham.codeplayground.exception;

public class FileNotFoundInDatabaseException extends RuntimeException {
    public FileNotFoundInDatabaseException(String message) {
        super(message);
    }
}
