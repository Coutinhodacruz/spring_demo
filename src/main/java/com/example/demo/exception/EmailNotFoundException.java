package com.example.demo.exception;

public class EmailNotFoundException extends Throwable {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
