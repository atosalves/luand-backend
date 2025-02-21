package com.luand.luand.entities.dto.image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateImageDTO(
                @NotBlank(message = "{name.not-blank}") @Size(min = 3, max = 72, message = "{name.size}") String nameKey,
                @NotBlank(message = "{url.not-blank}") String url
) {
        
}
