package com.shubham.codeplayground.exception;

public class InvalidTestcaseFormatException extends RuntimeException {
    public InvalidTestcaseFormatException(String message) {
        super(message);
    }

    public InvalidTestcaseFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
