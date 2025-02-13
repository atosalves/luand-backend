package com.luand.luand.entities.dto.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateModelDTO(
                @NotBlank(message = "Name is required") @Size(min = 3, max = 72, message = "Name must be between 3 and 72 characters") String name,
                @NotBlank(message = "Username is required") @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters") String description,
                @Positive(message = "Price must be greater than zero") @NotNull(message = "Price is required") BigDecimal price,
                @NotEmpty(message = "At least one size is required") Set<com.luand.luand.entities.Size> supportedSizes) {

}
