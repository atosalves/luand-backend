package com.luand.luand.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.luand.luand.entities.dto.TokenResponseDTO;
import com.luand.luand.entities.dto.user.LoginDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;

    public TokenResponseDTO authenticate(LoginDTO data) {
        var user = userService.getUserByEmail(data.email());

        var isValid = userService.validatePassword(data.password(), user.getPassword());
        if (!isValid) {
            throw new BadCredentialsException("Invalid password");
        }

        return tokenService.getToken(user.getEmail());
    }



}
