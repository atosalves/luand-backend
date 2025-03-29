package com.luand.luand.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luand.luand.entities.Model;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.repositories.ModelRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ModelControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        ModelRepository modelRepository;

        List<Model> modelEntities;

        @BeforeEach
        void setUp() {
                var createModelDTO1 = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var createModelDTO2 = new CreateModelDTO(
                                "name_test_2",
                                "ref_test_2",
                                "description_test_2",
                                BigDecimal.valueOf(20),
                                Set.of(Size.P, Size.M));
                var createModelDTO3 = new CreateModelDTO(
                                "name_test_3",
                                "ref_test_3",
                                "description_test_3",
                                BigDecimal.valueOf(30),
                                Set.of(Size.P, Size.M));

                var model1 = new Model(createModelDTO1);
                var model2 = new Model(createModelDTO2);
                var model3 = new Model(createModelDTO3);

                modelEntities = modelRepository.saveAll(Arrays.asList(model1, model2, model3));
        }

        @AfterEach
        void tearDown() {
                modelEntities.removeAll(modelEntities);
                modelRepository.deleteAll();
        }

        @Test
        void shouldReturnAllModels() throws Exception {

                var indexModel = 0;
                var modelId = modelEntities.get(0).getId();

                mockMvc.perform(get("/models").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(modelEntities.size()))
                                .andExpect(jsonPath("$[" + indexModel + "].id").value(modelId));
        }

        @Test
        void shouldCreateModel() throws Exception {
                var createModelDTO = new CreateModelDTO(
                                "name_test_4",
                                "ref_test_4",
                                "description_test_4",
                                BigDecimal.valueOf(40),
                                Set.of(Size.P, Size.M));

                mockMvc.perform(post("/models").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createModelDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").isNotEmpty())
                                .andExpect(jsonPath("$.name").value(createModelDTO.name()))
                                .andExpect(jsonPath("$.ref").value(createModelDTO.ref()))
                                .andExpect(jsonPath("$.description").value(createModelDTO.description()))
                                .andExpect(jsonPath("$.price").value(createModelDTO.price()))
                                .andExpect(jsonPath("$.supportedSizes[0]").value("P"))
                                .andExpect(jsonPath("$.supportedSizes[1]").value("M"));
        }

        @Test
        void shouldReturnBadRequestWithInvalidCreateDTO() throws Exception {
                var createModelDTO = new CreateModelDTO(
                                "er",
                                "too_long_ref_test_4",
                                "er",
                                BigDecimal.valueOf(-1),
                                Set.of(Size.P, Size.M));

                mockMvc.perform(post("/models").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createModelDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldUpdateModel() throws Exception {
                var modelId = modelEntities.get(0).getId();
                var updateModelDTO = new UpdateModelDTO(
                                "updated_name_test",
                                "ref_test",
                                "updated_description_test",
                                BigDecimal.valueOf(80),
                                Set.of(Size.GG));

                mockMvc.perform(put("/models/" + modelId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateModelDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(modelId))
                                .andExpect(jsonPath("$.name").value(updateModelDTO.name()))
                                .andExpect(jsonPath("$.ref").value(updateModelDTO.ref()))
                                .andExpect(jsonPath("$.description").value(updateModelDTO.description()))
                                .andExpect(jsonPath("$.price").value(updateModelDTO.price()))
                                .andExpect(jsonPath("$.supportedSizes[0]").value("GG"));
        }

        @Test
        void shouldReturnBadRequestWithInvalidUpdateDTO() throws Exception {
                var modelId = modelEntities.get(0).getId();
                var updateModelDTO = new UpdateModelDTO(
                                "er",
                                "too_long_ref_test_4",
                                "er",
                                BigDecimal.valueOf(-1),
                                Set.of(Size.P, Size.M));

                mockMvc.perform(put("/models/" + modelId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateModelDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentModel() throws Exception {
                var modelId = 999L;
                var updateModelDTO = new UpdateModelDTO(
                                "updated_name_test",
                                "ref_test",
                                "updated_description_test",
                                BigDecimal.valueOf(80),
                                Set.of(Size.GG));

                mockMvc.perform(put("/models/" + modelId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateModelDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeleteModel() throws Exception {
                var modelId = modelEntities.get(0).getId();

                mockMvc.perform(delete("/models/" + modelId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentModel() throws Exception {
                var modelId = 999L;

                mockMvc.perform(delete("/models/" + modelId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

}
