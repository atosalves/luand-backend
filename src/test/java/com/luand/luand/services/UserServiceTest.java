package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.exceptions.custom_exception.user.UserNotFoundException;
import com.luand.luand.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Test
    void shouldReturnUserById() {
        var createUserDTO = new CreateUserDTO(
                "name_test",
                "email_test",
                "password_test");

        var user = new User(createUserDTO);

        var userId = 1L;
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var result = userService.getUserById(userId);

        assertNotNull(result);

        assertEquals(userId, result.getId());
        assertEquals("name_test", result.getName());
        assertEquals("email_test", result.getEmail());
        assertEquals("password_test", result.getPassword());

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowNotFoundUserWhenUserById() {
        var userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        verify(userRepository).findById(userId);
    }
}
