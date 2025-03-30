package com.luand.luand.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.LoginDTO;
import com.luand.luand.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        UserService userService;

        User userEntity;

        @BeforeEach
        void setUp() {
                var userDTO = new CreateUserDTO("name_test", "email_test@gmail.com", "raw_password_test");

                userEntity = userService.createUser(userDTO);
        }

        @AfterEach
        void tearDown() {
                userService.deleteUser(userEntity.getId());
        }

        @Test
        void shouldLogin() throws Exception {

                var loginDTO = new LoginDTO(userEntity.getEmail(), "raw_password_test");

                mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isNoContent())
                                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("token=")));
        }

        @Test
        void shouldReturnBadRequestWithInvalidLoginDTO() throws Exception {
                var loginDTO = new LoginDTO("", "");

                mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWithInvalidEmail() throws Exception {
                var loginDTO = new LoginDTO("any_email@gmail.com", "raw_password_test");

                mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnBadCredentialsWithInvalidPassword() throws Exception {
                var loginDTO = new LoginDTO(userEntity.getEmail(), "wrong_password_test");

                mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isUnauthorized());
        }

}
