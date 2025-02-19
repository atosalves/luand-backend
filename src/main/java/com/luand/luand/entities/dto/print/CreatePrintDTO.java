package com.luand.luand.entities.dto.print;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePrintDTO(
                @NotBlank(message = "Name is required") @Size(min = 3, max = 72, message = "Name must be between 3 and 72 characters") String name,
                @NotBlank(message = "Image is required") @Size(min = 3, max = 72) String image,
                @NotNull(message = "Model is required") Long modelId) {

}