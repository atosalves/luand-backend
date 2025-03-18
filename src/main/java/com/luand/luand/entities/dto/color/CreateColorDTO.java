package com.luand.luand.entities.dto.color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColorDTO(
                @NotBlank(message = "{name.not-blank}") @Size(min = 3, max = 72, message = "{name.size}") String name,
                @NotBlank(message = "{color.not-blank}") @Size(min = 3, max = 72, message = "{color.size}") String hexColor,
                @NotBlank(message = "{ref.not-blank}") @Size(max = 12, message = "{ref.size}") String ref,
                @NotNull(message = "{print-id.not-null}") Long printId) {
}