package com.luand.luand.exceptions.custom_exception.user;

public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Usuário não encontrado";
    }
}
