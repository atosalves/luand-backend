package com.luand.luand.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.ItemController;
import com.luand.luand.exceptions.ErrorResponse;
import com.luand.luand.exceptions.custom_exception.item.*;

@RestControllerAdvice(assignableTypes = ItemController.class)
public class ItemExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFound(ItemNotFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

}
