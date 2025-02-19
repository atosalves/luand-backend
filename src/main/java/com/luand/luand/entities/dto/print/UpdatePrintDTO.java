package com.luand.luand.entities.dto.print;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePrintDTO(
                @NotBlank(message = "Name is required") @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String name,
                @NotBlank(message = "Print is required") @Size(min = 3, max = 20, message = "Print must be between 3 and 20 characters") String print,
                @NotNull(message = "Model is required") Long modelId) {

}
