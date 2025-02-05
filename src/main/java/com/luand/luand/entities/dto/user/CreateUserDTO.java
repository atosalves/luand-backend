package com.luand.luand.entities.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
                @NotBlank(message = "Name is required") @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters") String name,
                @NotBlank(message = "Password is required") @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters") String password,
                @Email(message = "Invalid email") @NotBlank(message = "Email is required") String email,
                @NotNull(message = "Store is required") Long storeId) {

}
