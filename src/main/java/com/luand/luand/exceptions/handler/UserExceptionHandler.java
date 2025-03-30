package com.luand.luand.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luand.luand.controllers.AuthController;
import com.luand.luand.controllers.UserController;
import com.luand.luand.exceptions.ErrorResponse;
import com.luand.luand.exceptions.custom_exception.user.UserNotFoundException;

@RestControllerAdvice(assignableTypes = { UserController.class, AuthController.class })
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

}
