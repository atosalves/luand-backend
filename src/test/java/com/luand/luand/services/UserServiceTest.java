package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

    List<User> userEntities;

    @BeforeEach
    void setUp() {
        var createUserDTO1 = new CreateUserDTO(
                "name_test_1",
                "email_test_1",
                "password_test_1");
        var createUserDTO2 = new CreateUserDTO(
                "name_test_2",
                "email_test_2",
                "password_test_2");
        var createUserDTO3 = new CreateUserDTO(
                "name_test_3",
                "email_test_3",
                "password_test_3");

        var user1 = new User(createUserDTO1);
        var user2 = new User(createUserDTO2);
        var user3 = new User(createUserDTO3);

        userEntities = List.of(user1, user2, user3);
    }

    @Test
    void shouldReturnUserById() {
        var user = userEntities.get(0);
        var userId = user.getId();

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
        var user = userEntities.get(0);
        var email = user.getEmail();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var result = userService.getUserByEmail(email);

        assertNotNull(result);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());

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

        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(any(User.class))).thenAnswer(invoker -> invoker.getArgument(0));

        var createdUser = userService.createUser(createUserDTO);

        assertNotNull(createUserDTO);
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getPassword(), createdUser.getPassword());

        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shoudUpdateUser() {

        var user = userEntities.get(0);
        var userId = user.getId();

        var originalName = user.getName();
        var originalPassword = user.getPassword();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invoker -> invoker.getArgument(0));

        var updateUserDTO = new UpdateUserDTO(
                "updated_name_test",
                "updated_email_test",
                "updated_password_test");

        when(passwordEncoder.encode(anyString())).thenReturn(updateUserDTO.password());

        var updatedUser = userService.updateUser(user.getId(), updateUserDTO);

        assertNotNull(updateUserDTO);

        assertEquals(user.getId(), updatedUser.getId());

        assertNotEquals(originalName, updatedUser.getName());
        assertNotEquals(originalPassword, updatedUser.getPassword());

        assertEquals(updateUserDTO.email(), updatedUser.getEmail());

        verify(passwordEncoder).encode(updateUserDTO.password());
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUser() {
        var user = userEntities.get(0);
        var userId = user.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldReturnTrueWhenPasswordIsMatches() {
        var rawPassword = "password_test";
        var encodedPassword = "encoded_password_test";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        var validatedPassword = userService.validatePassword(rawPassword, encodedPassword);

        assertTrue(validatedPassword);

        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    void shouldReturnFalseWhenPasswordIsNotMatches() {
        var rawPassword = "password_test";
        var encodedPassword = "encoded_password_test";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        var validatedPassword = userService.validatePassword(rawPassword, encodedPassword);

        assertFalse(validatedPassword);

        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
}
