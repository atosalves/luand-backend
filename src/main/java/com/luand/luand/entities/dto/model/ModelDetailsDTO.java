package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;

import com.luand.luand.entities.Model;

public record ModelDetailsDTO(Long id, String name, String description, BigDecimal price) {
    public ModelDetailsDTO(Model model) {
        this(model.getId(), model.getName(), model.getDescription(), model.getPrice());
    }
}
