package com.luand.luand.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.UpdateUserDTO;
import com.luand.luand.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {
        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        UserService userService;

        List<User> userEntities;

        @BeforeEach
        void setUp() {
                var userDTO1 = new CreateUserDTO("name_test_1", "email_test_1@gmail.com", "password_test_1");
                var userDTO2 = new CreateUserDTO("name_test_2", "email_test_2@gmail.com", "password_test_2");
                var userDTO3 = new CreateUserDTO("name_test_3", "email_test_3@gmail.com", "password_test_3");

                var user1 = userService.createUser(userDTO1);
                var user2 = userService.createUser(userDTO2);
                var user3 = userService.createUser(userDTO3);

                userEntities = new ArrayList<>(List.of(user1, user2, user3));
        }

        @AfterEach
        void tearDown() {
                userEntities.forEach(user -> userService.deleteUser(user.getId()));
        }

        @Test
        void shouldReturnUserById() throws Exception {
                var user = userEntities.get(0);

                mockMvc.perform(get("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(user.getId()))
                                .andExpect(jsonPath("$.email").value(user.getEmail()))
                                .andExpect(jsonPath("$.name").value(user.getName()))
                                .andExpect(jsonPath("$.password").doesNotExist());
        }

        @Test
        void shouldReturnNotFoundWhenGettingByIdNonExistentUser() throws Exception {
                var invalidId = 999L;

                mockMvc.perform(get("/users/" + invalidId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

        }

        @Test
        void shouldReturnUserByEmail() throws Exception {
                var user = userEntities.get(0);

                mockMvc.perform(get("/users/email/" + user.getEmail()).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(user.getId()))
                                .andExpect(jsonPath("$.email").value(user.getEmail()))
                                .andExpect(jsonPath("$.name").value(user.getName()))
                                .andExpect(jsonPath("$.password").doesNotExist());
        }

        @Test
        void shouldReturnNotFoundWhenGettingByEmailNonExistentUser() throws Exception {
                var invalidEmail = "invalid_email@gmail.com";

                mockMvc.perform(get("/users/email/" + invalidEmail).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

        }

        @Test
        void shouldCreateUser() throws Exception {
                var userDTO4 = new CreateUserDTO("name_test_4", "email_test_4@gmail.com", "password_test_4");

                mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO4)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(userDTO4.email()))
                                .andExpect(jsonPath("$.name").value(userDTO4.name()))
                                .andExpect(jsonPath("$.password").doesNotExist());
        }

        @Test
        void shouldReturnBadRequestWithInvalidCreateDTO() throws Exception {
                var invalidDTO = new CreateUserDTO("", "email_test_4", "");

                mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldUpdateUser() throws Exception {
                var user = userEntities.get(0);

                var updateUserDTO = new UpdateUserDTO("updated_name", "updated_email@gmail.com", "updated_password");

                mockMvc.perform(put("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateUserDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(updateUserDTO.email()))
                                .andExpect(jsonPath("$.name").value(updateUserDTO.name()))
                                .andExpect(jsonPath("$.password").doesNotExist());
        }

        @Test
        void shouldReturnBadRequestWithInvalidUpdateDTO() throws Exception {
                var user = userEntities.get(0);

                var invalidUpdateDTo = new UpdateUserDTO("", "updated_email", "");

                mockMvc.perform(put("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUpdateDTo)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentUser() throws Exception {
                var invalidUpdateDTO = new UpdateUserDTO("updated_name", "updated_email@gmail.com", "updated_password");

                mockMvc.perform(put("/users/" + 999L).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUpdateDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeleteUser() throws Exception {
                var user = userEntities.get(0);

                userEntities.remove(user);

                mockMvc.perform(delete("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
                var invalidId = 999L;

                mockMvc.perform(delete("/users/" + invalidId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }
}
