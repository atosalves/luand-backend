package com.luand.luand.entities.dto.fashionLine;

import com.luand.luand.entities.FashionLine;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;

public record FashionLineDetailsDTO(
        Long id,
        String name,
        String print,
        ModelDetailsDTO modelDTO) {
    public FashionLineDetailsDTO(FashionLine fashionLine) {
        this(
                fashionLine.getId(),
                fashionLine.getName(),
                fashionLine.getPrint(),
                new ModelDetailsDTO(fashionLine.getModel()));
    }
}
