package com.luand.luand.exception.model;

public class ModelAlreadyExistsException extends RuntimeException {
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
