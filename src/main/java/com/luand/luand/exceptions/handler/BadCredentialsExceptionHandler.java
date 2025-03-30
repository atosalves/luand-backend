package com.luand.luand.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.exceptions.ErrorResponse;

@RestControllerAdvice
public class BadCredentialsExceptionHandler {

        @ExceptionHandler(BadCredentialsException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public ErrorResponse handleBadCredentials(BadCredentialsException exception) {
                return ErrorResponse.builder().message(exception.getMessage()).build();
        }

}
