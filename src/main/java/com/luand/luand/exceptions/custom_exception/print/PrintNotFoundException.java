package com.luand.luand.exceptions.custom_exception.print;

public class PrintNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Estampa não encontrada";
    }
}
