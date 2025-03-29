package com.luand.luand.entities.dto.print;

import java.util.Set;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;
import com.luand.luand.entities.Item;
import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;

public record PrintDetailsDTO(
        Long id,
        String name,
        String ref,
        Image coverImage,
        Set<Image> images,
        Set<Color> colors,
        Set<Item> itens,
        ModelDetailsDTO modelDetailsDTO) {
    public PrintDetailsDTO(Print print) {
        this(
                print.getId(),
                print.getName(),
                print.getRef(),
                print.getCoverImage(),
                print.getImages(),
                print.getColors(),
                print.getItens(),
                new ModelDetailsDTO(print.getModel()));
    }
}
