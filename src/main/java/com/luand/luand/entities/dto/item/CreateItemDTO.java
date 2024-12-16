package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateItemDTO(
        @NotNull(message = "Size is required") Size size,
        @NotBlank(message = "Color is required") @jakarta.validation.constraints.Size(min = 7, max = 9, message = "Color must be between 7 and 9 characters") String color,
        @PositiveOrZero(message = "Available quantity must be zero or greater") @NotNull(message = "Available quantity is required") int availableQuantity,
        @NotNull(message = "Fashion line is required") Long fashionLineId) {

}
