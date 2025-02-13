package com.luand.luand.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.user.LoginDTO;
import com.luand.luand.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login", description = "Authenticates a user with their username and password. If successful, sets a JWT token as an HTTP-only cookie for secure authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token set in cookie"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO data) {
        var tokenDTO = authService.authenticate(data);

        var jwtCookie = ResponseCookie.from("accessToken", tokenDTO.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(tokenDTO.accessToken());
    }


}
