package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.StoreController;
import com.luand.luand.exception.store.StoreAlreadyExistsException;
import com.luand.luand.exception.store.StoreNotFoundException;

@RestControllerAdvice(assignableTypes = StoreController.class)
public class StoreExceptionHandler {

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<String> handleStoreNotFound(StoreNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StoreAlreadyExistsException.class)
    public ResponseEntity<String> handleStoreAlreadyExists(StoreAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
