package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.LoginDTO;

@SpringBootTest
public class AuthServiceTest {

        @MockitoBean
        UserService userService;

        @Autowired
        AuthService authService;

        User userEntity;

        @BeforeEach
        void setUp() {
                var createUserDTO = new CreateUserDTO(
                                "name_test_1",
                                "email_test_1",
                                "password_test_1");

                userEntity = new User(createUserDTO);
        }

        @Test
        void shouldReturnTokenWhenValidatingPassword() {
                var loginDTO = new LoginDTO(userEntity.getEmail(), "password_test_1");

                when(userService.getUserByEmail(loginDTO.email())).thenReturn(userEntity);
                when(userService.validatePassword(loginDTO.password(), userEntity.getPassword())).thenReturn(true);

                var token = authService.authenticate(loginDTO);

                assertNotNull(token);

                verify(userService).getUserByEmail(loginDTO.email());
                verify(userService).validatePassword(loginDTO.password(), userEntity.getPassword());
        }

        @Test
        void shouldThrowBadCredentialsExceptionWhenValidatingPasswordWithDifferentPassword() {
                var loginDTO = new LoginDTO(userEntity.getEmail(), "password_test_2");

                when(userService.getUserByEmail(loginDTO.email())).thenReturn(userEntity);
                when(userService.validatePassword(loginDTO.password(), userEntity.getPassword())).thenReturn(false);

                assertThrows(BadCredentialsException.class, () -> authService.authenticate(loginDTO));

                verify(userService).getUserByEmail(loginDTO.email());
                verify(userService).validatePassword(loginDTO.password(), userEntity.getPassword());
        }

}
