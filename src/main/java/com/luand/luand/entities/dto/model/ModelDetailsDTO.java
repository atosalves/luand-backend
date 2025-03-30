package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;
import java.util.Set;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.Size;

public record ModelDetailsDTO(Long id, String ref, String name, String description, BigDecimal price,
        Set<Size> supportedSizes) {
    public ModelDetailsDTO(Model model) {
        this(model.getId(), model.getRef(), model.getName(), model.getDescription(), model.getPrice(),
                model.getSupportedSizes());
    }
}
