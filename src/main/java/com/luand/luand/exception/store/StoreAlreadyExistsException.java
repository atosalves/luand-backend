package com.luand.luand.exception.store;

public class StoreAlreadyExistsException extends RuntimeException {
    public StoreAlreadyExistsException(String message) {
        super(message);
    }
}
