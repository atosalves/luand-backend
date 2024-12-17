package com.luand.luand.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.user.LoginDTO;
import com.luand.luand.entities.dto.user.TokenResponseDTO;
import com.luand.luand.services.TokenService;
import com.luand.luand.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "User login", description = "Authenticate a user with their username and password, returning a JWT token for further requests.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token generated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO data) {
        var user = userService.getUserByUsername(data.username());

        userService.validatePassword(data.password(), user.getPassword());

        var jwtValue = tokenService.generateToken(user.getId().toString());
        var expireTime = tokenService.getExpirationTime();

        return ResponseEntity.ok(new TokenResponseDTO(jwtValue, expireTime));
    }

}
