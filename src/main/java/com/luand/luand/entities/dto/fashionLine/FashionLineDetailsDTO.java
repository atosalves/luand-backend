package com.luand.luand.entities.dto.fashionLine;

import java.util.Set;

import com.luand.luand.entities.FashionLine;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;

public record FashionLineDetailsDTO(
        Long id,
        String name,
        String print,
        ModelDetailsDTO modelDTO,
        Set<String> colors,
        Set<Size> sizes) {
    public FashionLineDetailsDTO(FashionLine fashionLine) {
        this(
                fashionLine.getId(),
                fashionLine.getName(),
                fashionLine.getPrint(),
                new ModelDetailsDTO(fashionLine.getModel()),
                fashionLine.getColorsDistinct(),
                fashionLine.getSizesDistinct());
    }
}
