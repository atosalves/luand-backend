package com.luand.luand.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.user.LoginDTO;
import com.luand.luand.entities.dto.user.TokenResponseDTO;
import com.luand.luand.services.TokenService;
import com.luand.luand.services.UserService;

@RestController
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        var user = userService.getUserByUsername(loginDTO.username());

        userService.validatePassword(loginDTO.password(), user.getPassword());

        var jwtValue = tokenService.generateToken(user.getId().toString());
        var expireTime = tokenService.getExpirationTime();

        return ResponseEntity.ok(new TokenResponseDTO(jwtValue, expireTime));
    }
}
