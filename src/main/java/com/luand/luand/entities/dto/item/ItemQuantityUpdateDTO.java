package com.luand.luand.entities.dto.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemQuantityUpdateDTO(
                @PositiveOrZero(message = "Quantity must be greater than zero") @NotNull(message = "Quantity is required") int quantity) {

}
