package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateModelDTO(
                @NotBlank(message = "{name.not-blank}") @Size(min = 3, max = 72, message = "{name.size}") String name,
                @NotBlank(message = "{ref.not-blank}") @Size(max = 12, message = "{ref.size}") String ref,
                @NotBlank(message = "{description.not-blank}") @Size(min = 3, max = 255, message = "{description.size}") String description,
                @NotNull(message = "{price.not-null}") @Positive(message = "{price.positive}") BigDecimal price,
                @NotEmpty(message = "{supported-sizes.not-empty}") Set<com.luand.luand.entities.Size> supportedSizes) {

}
