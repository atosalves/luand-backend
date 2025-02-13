package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.ModelController;
import com.luand.luand.exception.model.ModelAlreadyExistsException;
import com.luand.luand.exception.model.ModelNotFoundException;

@RestControllerAdvice(assignableTypes = ModelController.class)
public class ModelExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> handleModelNotFound(ModelNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModelAlreadyExistsException.class)
    public ResponseEntity<String> handleModelAlreadyExists(ModelAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
