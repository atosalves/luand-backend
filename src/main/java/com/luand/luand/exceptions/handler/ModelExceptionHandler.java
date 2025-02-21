package com.luand.luand.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.ModelController;
import com.luand.luand.exceptions.ErrorResponse;
import com.luand.luand.exceptions.custom_exception.model.*;

@RestControllerAdvice(assignableTypes = ModelController.class)
public class ModelExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleModelNotFound(ModelNotFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

}
