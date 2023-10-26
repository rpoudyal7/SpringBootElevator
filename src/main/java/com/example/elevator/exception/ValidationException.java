package com.example.elevator.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationException extends Exception {


    private List<ObjectError> errors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }


    public List<ObjectError> getErrors() {
        return errors;
    }
}
