package com.example.demo.exception;

public class InvalidStudentIdException extends Throwable {
    public InvalidStudentIdException(String message) {
        super(message);
    }
}
