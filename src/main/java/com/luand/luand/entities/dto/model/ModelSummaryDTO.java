package com.luand.luand.entities.dto.model;

import com.luand.luand.entities.Model;

public record ModelSummaryDTO(Long id, String name) {
        public ModelSummaryDTO(Model model) {
                this(model.getId(), model.getName());
        }
}
