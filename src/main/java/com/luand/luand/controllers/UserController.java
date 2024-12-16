package com.luand.luand.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.UserSummaryDTO;
import com.luand.luand.services.UserService;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserSummaryDTO> createUser(@RequestBody CreateUserDTO userDTO) {
        var user = userService.createUser(userDTO);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }
}
