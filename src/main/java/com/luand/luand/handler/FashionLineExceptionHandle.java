package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luand.luand.controllers.FashionLineController;
import com.luand.luand.exception.fashionLine.FashionLineAlreadyExistsException;
import com.luand.luand.exception.fashionLine.FashionLineNotFoundException;

@ControllerAdvice(assignableTypes = FashionLineController.class)
public class FashionLineExceptionHandle {

    @ExceptionHandler(FashionLineNotFoundException.class)
    public ResponseEntity<String> handleFashionLineNotFound(FashionLineNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FashionLineAlreadyExistsException.class)
    public ResponseEntity<String> handleFashionLineAlreadyExists(FashionLineAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
