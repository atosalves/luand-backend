package com.luand.luand.controllers;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.print.UpdatePrintDTO;
import com.luand.luand.repositories.ImageRepository;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.services.ModelService;
import com.luand.luand.services.PrintService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PrintControllerTest {
        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        PrintService printService;

        static Color color;

        @Autowired
        ModelService modelService;

        @Autowired
        ImageRepository imageRepository;

        @BeforeAll
        static void setUp() {
                color = new Color("#000", "color", "ref");
        }

        @Test
        void shouldReturnAllPrints() throws Exception {
                mockMvc.perform(get("/prints").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void shouldCreatePrint() throws Exception {
                var image1 = new Image("name_test_1", "url_test_1");
                var image2 = new Image("name_test_2", "url_test_2");
                var image3 = new Image("name_test_3", "url_test_3");

                var model = modelService.createModel(new CreateModelDTO("model_1", "ref_1", "model_1",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, Set.of(image2, image3),
                                Set.of(color), model.getId());
                mockMvc.perform(post("/prints").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(printDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").isNotEmpty())
                                .andExpect(jsonPath("$.name").value(printDTO.name()))
                                .andExpect(jsonPath("$.ref").value(printDTO.ref()))
                                .andExpect(jsonPath("$.images.length()").value(printDTO.images().size()));
        }

        @Test
        void shouldReturnBadRequestWithInvalidCreateDTO() throws Exception {
                var image4 = new Image("name_test_4", "url_test_4");
                var image5 = new Image("name_test_5", "url_test_5");
                var image6 = new Image("name_test_6", "url_test_6");

                var model = modelService.createModel(new CreateModelDTO("model_2", "ref_2", "model_2",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var invalidPrintDTO = new CreatePrintDTO(
                                "n", "too_long_ref_test_2", image4,
                                Set.of(image5, image6),
                                Set.of(color), model.getId());

                mockMvc.perform(post("/prints").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidPrintDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldUpdatePrint() throws Exception {
                var image7 = new Image("name_test_7", "url_test_7");
                var image8 = new Image("name_test_8", "url_test_8");
                var image9 = new Image("name_test_9", "url_test_9");

                var model = modelService.createModel(new CreateModelDTO("model_3", "ref_3", "model_3",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_3", "ref_test_3", image7,
                                Set.of(image8, image9),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                var updateImage7 = new Image("update_name_test_7", "update_url_test_7");
                var updateImage8 = new Image("update_name_test_8", "update_url_test_8");
                var updateImage9 = new Image("update_name_test_9", "update_url_test_9");

                var updatePrintDTO = new UpdatePrintDTO(
                                "updated_name_test_3", "up_ref_3", updateImage7,
                                Set.of(updateImage8, updateImage9),
                                Set.of(color), model.getId());

                mockMvc.perform(put("/prints/" +
                                printId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatePrintDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(printId))
                                .andExpect(jsonPath("$.name").value(updatePrintDTO.name()))
                                .andExpect(jsonPath("$.ref").value(updatePrintDTO.ref()));
        }

        @Test
        void shouldReturnBadRequestWithInvalidUpdateDTO() throws Exception {
                var image10 = new Image("name_test_10", "url_test_10");
                var image11 = new Image("name_test_11", "url_test_11");
                var image12 = new Image("name_test_12", "url_test_12");

                var model = modelService.createModel(new CreateModelDTO("model_4", "ref_4", "model_4",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_4", "ref_test_4", image10,
                                Set.of(image11, image12),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                var updatePrintDTO = new UpdatePrintDTO(
                                "updated_name_test_4", "too_long_updated_ref_4", image10,
                                Set.of(image11, image12),
                                Set.of(color), model.getId());

                mockMvc.perform(put("/prints/" +
                                printId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatePrintDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentPrint() throws Exception {
                var printId = 999L;

                var image13 = new Image("name_test_13", "url_test_13");
                var image14 = new Image("name_test_14", "url_test_14");
                var image15 = new Image("name_test_15", "url_test_15");

                var model = modelService.createModel(new CreateModelDTO("model_5", "ref_5", "model_5",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var updatePrintDTO = new UpdatePrintDTO(
                                "updated_name_test_5", "up_ref_5", image13,
                                Set.of(image14, image15),
                                Set.of(color), model.getId());

                mockMvc.perform(put("/prints/" + printId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatePrintDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeletePrint() throws Exception {
                var image16 = new Image("name_test_16", "url_test_16");
                var image17 = new Image("name_test_17", "url_test_17");
                var image18 = new Image("name_test_18", "url_test_18");

                var model = modelService.createModel(new CreateModelDTO("model_6", "ref_6", "model_6",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_6", "ref_test_6", image16,
                                Set.of(image17, image18),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                mockMvc.perform(delete("/prints/" +
                                printId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentPrint() throws Exception {
                var printId = 999L;

                mockMvc.perform(delete("/prints/" + printId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }
}
