package com.luand.luand.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {

        private final TokenService tokenService;

        @Operation(summary = "User login", description = "Authenticates a user with their username and password. If successful, sets a JWT token as an HTTP-only cookie for secure authentication.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login successful, JWT token set in cookie"),
                        @ApiResponse(responseCode = "404", description = "User not found"),
                        @ApiResponse(responseCode = "401", description = "Invalid username or password")
        })
        @PostMapping("/refresh")
        public ResponseEntity<String> refreshAccessToken(@RequestBody @NotNull @Valid String refreshToken) {
                var tokenDTO = tokenService.refreshAccessToken(refreshToken);
                return ResponseEntity.ok(tokenDTO.accessToken());
        }

}
