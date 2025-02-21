package com.luand.luand.entities.dto.print;

import java.util.Set;

import com.luand.luand.entities.Image;
import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;

public record PrintDetailsDTO(
        Long id,
        String name,
        Image coverImage,
        Set<Image> images,
        ModelDetailsDTO modelDetailsDTO) {
    public PrintDetailsDTO(Print print) {
        this(
                print.getId(),
                print.getName(),
                print.getCoverImage(),
                print.getImages(),
                new ModelDetailsDTO(print.getModel()));
    }
}
