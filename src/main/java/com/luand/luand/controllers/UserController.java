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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserSummaryDTO> getUserByUsername(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserSummaryDTO> getUserByEmail(@PathVariable String email) {
        var user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserSummaryDTO> createUser(@RequestBody @Valid CreateUserDTO data) {
        var user = userService.createUser(data);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO data) {
        var user = userService.updateUser(id, data);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
