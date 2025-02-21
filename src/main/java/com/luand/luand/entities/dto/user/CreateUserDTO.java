package com.luand.luand.entities.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
                @NotBlank(message = "{name.not-blank}") @Size(min = 2, max = 30, message = "{name.size}") String name,
                @Email(message = "{email.invalid}") @NotBlank(message = "{email.not-blank}") String email,
                @NotBlank(message = "{password.not-blank}") @Size(min = 6, max = 20, message = "{password.size}") String password) {

}
