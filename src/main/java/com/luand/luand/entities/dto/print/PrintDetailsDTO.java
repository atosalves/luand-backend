package com.luand.luand.entities.dto.print;

import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.model.ModelSummaryDTO;

public record PrintDetailsDTO(
        Long id,
        String name,
        String image,
        ModelSummaryDTO modelSummaryDTO) {
    public PrintDetailsDTO(Print fashionLine) {
        this(
                fashionLine.getId(),
                fashionLine.getName(),
                fashionLine.getImage(),
                new ModelSummaryDTO(fashionLine.getModel()));
    }
}
