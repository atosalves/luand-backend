package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.PrintController;
import com.luand.luand.exception.print.PrintNotFoundException;

@RestControllerAdvice(assignableTypes = PrintController.class)
public class PrintExceptionHandler {

    @ExceptionHandler(PrintNotFoundException.class)
    public ResponseEntity<String> handlePrintNotFound(PrintNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
