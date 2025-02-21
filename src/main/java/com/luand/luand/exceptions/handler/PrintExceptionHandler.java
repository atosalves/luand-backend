package com.luand.luand.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.PrintController;
import com.luand.luand.exceptions.ErrorResponse;
import com.luand.luand.exceptions.custom_exception.print.*;

@RestControllerAdvice(assignableTypes = PrintController.class)
public class PrintExceptionHandler {

    @ExceptionHandler(PrintNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePrintNotFound(PrintNotFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }
}
