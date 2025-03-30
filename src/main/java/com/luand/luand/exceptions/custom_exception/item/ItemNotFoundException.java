package com.luand.luand.exceptions.custom_exception.item;

public class ItemNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Item não encontrado";
    }
}
