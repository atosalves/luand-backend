package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateItemDTO(
                @NotNull(message = "{size.not-null}") Size size,
                @NotBlank(message = "{color.not-blank}") Color color,
                @NotNull(message = "{available-quantity.not-null}") @Positive(message = "{available-quantity.positive}") int availableQuantity,
                @NotNull(message = "{print-id.not-null}") Long printId) {

}
