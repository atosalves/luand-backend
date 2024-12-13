package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;

import com.luand.luand.entities.Model;

public record ModelDetailsDTO(String name, String description, BigDecimal price) {
    public ModelDetailsDTO(Model model) {
        this(model.getName(), model.getDescription(), model.getPrice());
    }
}
