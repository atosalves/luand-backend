package com.luand.luand.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.UpdateUserDTO;
import com.luand.luand.entities.dto.user.UserSummaryDTO;
import com.luand.luand.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve a user by ID", description = "Fetch the details of a specific user using their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @Operation(summary = "Retrieve a user by username", description = "Fetch the details of a specific user using their username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserSummaryDTO> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve") @PathVariable String username) {
        var user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @Operation(summary = "Retrieve a user by email", description = "Fetch the details of a specific user using their email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserSummaryDTO> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve") @PathVariable String email) {
        var user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSummaryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email is already in use"),
            @ApiResponse(responseCode = "409", description = "Username is already in use")
    })
    @PostMapping
    public ResponseEntity<UserSummaryDTO> createUser(@RequestBody @Valid CreateUserDTO data) {
        var user = userService.createUser(data);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @Operation(summary = "Update a user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email is already in use"),
            @ApiResponse(responseCode = "409", description = "Username is already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @RequestBody @Valid UpdateUserDTO data) {
        var user = userService.updateUser(id, data);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @Operation(summary = "Delete a user", description = "Remove a user from the system by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
