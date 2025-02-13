package com.luand.luand.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.entities.Order;
import com.luand.luand.exception.order.OrderNotFoundException;

@RestControllerAdvice(assignableTypes = Order.class)
public class OrderExceptionHandler {

        @ExceptionHandler(OrderNotFoundException.class)
        public ResponseEntity<String> handleModelNotFound(OrderNotFoundException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
}
