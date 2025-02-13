package com.luand.luand.entities.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateStoreDTO(
        @NotBlank(message = "Name is required") @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String name,
                @Size(max = 72, message = "Description must be between 3 and 72 characters") String description) {

}