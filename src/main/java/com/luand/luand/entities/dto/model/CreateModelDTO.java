package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateModelDTO(
        @NotBlank(message = "Name is required") @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String name,
        @NotBlank(message = "Username is required") @Size(min = 3, max = 72, message = "Description must be between 3 and 72 characters") String description,
        @Positive(message = "Price must be greater than zero") @NotNull(message = "Price is required") BigDecimal price) {

}
