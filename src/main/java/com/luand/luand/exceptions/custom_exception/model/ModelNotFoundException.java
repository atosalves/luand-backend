package com.luand.luand.exceptions.custom_exception.model;

public class ModelNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Modelo não encontrado";
    }
}
