package com.luand.luand.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.oauth2.jwt.Jwt;

// import com.luand.luand.entities.User;
// import com.luand.luand.entities.dto.user.CreateUserDTO;
// import com.luand.luand.entities.dto.user.LoginDTO;
// import com.luand.luand.exception.user.UserNotFoundException;

public class AuthServiceTest {

    // @Mock
    // private UserService userService;

    // @Mock
    // private JwtEncoder jwtEncoder;

    // @InjectMocks
    // private AuthService authService;

    // @BeforeEach
    // void setup() {
    // MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // @DisplayName("Should return access token if username and password are
    // correct")
    // void testAuthenticate_SuccessAuthenticate() {
    // // arrange
    // var user = new User(new CreateUserDTO("testeteste", "12345678",
    // "teste@gmail.com"));
    // user.setId(1L);

    // var login = new LoginDTO("testeteste", "12345678");

    // when(userService.getUserByUsername(login.name())).thenReturn(user);
    // when(userService.validatePassword(login.password(),
    // user.getPassword())).thenReturn(true);

    // var jwtMock = mock(Jwt.class);
    // when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwtMock);
    // when(jwtMock.getTokenValue()).thenReturn("token");

    // // act
    // String token = authService.authenticate(login);

    // // assert
    // assertEquals("token", token, "Token should match expected value");

    // verify(userService, times(1)).getUserByUsername(anyString());
    // verify(userService, times(1)).validatePassword(anyString(), anyString());
    // verify(jwtEncoder, times(1)).encode(any());
    // verify(jwtMock, times(1)).getTokenValue();
    // }

    // @Test
    // @DisplayName("Should throw exception if user not found")
    // void testAuthenticate_ThrowExceptionForIncorrectUsername() {
    // // arrange
    // var login = new LoginDTO("nonExistent", "12345678");

    // when(userService.getUserByUsername(login.name())).thenThrow(UserNotFoundException.class);

    // // assert
    // assertThrows(UserNotFoundException.class,
    // () -> authService.authenticate(new LoginDTO(login.name(),
    // login.password())));

    // verify(userService, times(1)).getUserByUsername(anyString());
    // }

    // @Test
    // @DisplayName("Should throw exception if password is incorrect")
    // void testAuthenticate_ThrowExceptionForIncorrectPassword() {
    // // arrange
    // var user = new User(new CreateUserDTO("testeteste", "12345678",
    // "teste@gmail.com"));
    // user.setId(1L);

    // var login = new LoginDTO("testeteste", "12345678");

    // when(userService.getUserByUsername(login.name())).thenReturn(user);

    // when(userService.validatePassword(login.password(),
    // user.getPassword())).thenReturn(false);

    // // act
    // assertThrows(BadCredentialsException.class,
    // () -> authService.authenticate(new LoginDTO(login.name(),
    // login.password())));

    // verify(userService, times(1)).getUserByUsername(anyString());
    // verify(userService, times(1)).validatePassword(anyString(), anyString());
    // }
}
