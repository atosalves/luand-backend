package com.luand.luand.exception.order;

public class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
                super(message);
        }
}
