package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.luand.luand.entities.dto.user.UpdateUserDTO;
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

    @Test
    void shouldReturnUserByEmail() {
        var createUserDTO = new CreateUserDTO(
                "name_test",
                "email_test",
                "password_test");

        var user = new User(createUserDTO);

        var email = "email_test";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var result = userService.getUserByEmail(email);

        assertNotNull(result);

        assertEquals("name_test", result.getName());
        assertEquals("email_test", result.getEmail());
        assertEquals("password_test", result.getPassword());

        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowNotFoundUserWhenUserByEmail() {
        var email = "email_test";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));

        verify(userRepository).findByEmail(email);
    }

    @Test
    void shoudCreateUser() {
        var createUserDTO = new CreateUserDTO(
                "name_test",
                "email_test",
                "password_test");

        var user = new User(createUserDTO);

        when(passwordEncoder.encode(anyString())).thenReturn("password_test");
        when(userRepository.save(any(User.class))).thenAnswer(invoker -> invoker.getArgument(0));

        var createdUser = userService.createUser(createUserDTO);

        assertNotNull(createUserDTO);
        assertEquals("name_test", createdUser.getName());
        assertEquals("email_test", createdUser.getEmail());
        assertEquals("password_test", createdUser.getPassword());

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(user);
    }

    @Test
    void shoudUpdateUser() {

        var createUserDTO = new CreateUserDTO(
                "name_test",
                "email_test",
                "password_test");

        var user = new User(createUserDTO);
        var userId = 1L;
        user.setId(userId);

        when(passwordEncoder.encode(anyString())).thenReturn("updated_password_test");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invoker -> invoker.getArgument(0));

        var updateUserDTO = new UpdateUserDTO(
                "updated_name_test",
                "updated_email_test",
                "updated_password_test");

        var updatedUser = userService.updateUser(user.getId(), updateUserDTO);

        assertNotNull(updateUserDTO);
        assertEquals("updated_name_test", updatedUser.getName());
        assertEquals("updated_email_test", updatedUser.getEmail());
        assertEquals("updated_password_test", updatedUser.getPassword());

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(user);
    }
}
