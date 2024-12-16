package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luand.luand.controllers.ItemController;
import com.luand.luand.exception.item.ItemAlreadyExistsException;
import com.luand.luand.exception.item.ItemNotFoundException;

@ControllerAdvice(assignableTypes = ItemController.class)
public class ItemExceptionHandle {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<String> handleItemAlreadyExists(ItemAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
