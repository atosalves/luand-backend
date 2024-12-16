package com.luand.luand.entities.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
                @NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") String username,
                @NotBlank(message = "Password is required") @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters") String password) {

}
