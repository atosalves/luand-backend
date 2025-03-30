package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;

import com.luand.luand.entities.Model;

public record ModelSummaryDTO(Long id, String name, BigDecimal price) {
        public ModelSummaryDTO(Model model) {
                this(model.getId(), model.getName(), model.getPrice());
        }
}
