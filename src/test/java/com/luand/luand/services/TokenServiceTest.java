package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenServiceTest {

        @Autowired
        TokenService tokenService;

        @Test
        void shouldReturnToken() {
                var userIdentity = "user_identity_test";

                var token = tokenService.getToken(userIdentity);

                assertNotNull(token);
        }
}
