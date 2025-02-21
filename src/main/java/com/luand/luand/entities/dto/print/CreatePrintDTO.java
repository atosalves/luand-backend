package com.luand.luand.entities.dto.print;

import java.util.Set;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePrintDTO(
                @NotBlank(message = "{name.not-blank}") @Size(min = 3, max = 72, message = "{name.size}") String name,
                @NotBlank(message = "{ref.not-blank}") @Size(max = 12, message = "{ref.size}") String ref,
                @NotNull(message = "{image.not-null}") Image coverImage,
                Set<Image> images,
                @NotEmpty Set<Color> colors,
                @NotNull(message = "{model-id.not-null}") Long modelId) {

}