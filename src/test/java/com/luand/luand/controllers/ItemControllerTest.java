package com.luand.luand.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.services.ItemService;
import com.luand.luand.services.ModelService;
import com.luand.luand.services.PrintService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ItemControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        ItemService itemService;

        @Autowired
        ModelService modelService;

        @Autowired
        PrintService printService;

        static Color color;

        @BeforeAll
        static void setUp() {
                color = new Color("#000", "color", "ref");
        }

        @Test
        void shouldReturnAllItems() throws Exception {
                mockMvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void shouldCreateItem() throws Exception {
                var image1 = new Image("name_test_1", "url_test_1");
                var image2 = new Image("name_test_2", "url_test_2");
                var image3 = new Image("name_test_3", "url_test_3");

                var model = modelService.createModel(new CreateModelDTO("model_1", "ref_1", "model_1",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, Set.of(image2, image3),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                var itemDTO = new CreateItemDTO(Size.G, color, 10, printId);

                mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(itemDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$.size").value("G"))
                                .andExpect(jsonPath("$.color.name").value("color"))
                                .andExpect(jsonPath("$.availableQuantity").value(10))
                                .andExpect(jsonPath("$.printDTO.id").value(printId));
        }

        @Test
        void shouldReturnBadRequestWithInvalidCreateDTO() throws Exception {
                var image4 = new Image("name_test_4", "url_test_4");
                var image5 = new Image("name_test_5", "url_test_5");
                var image6 = new Image("name_test_6", "url_test_6");

                var model = modelService.createModel(new CreateModelDTO("model_2", "ref_2", "model_2",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_2", "ref_test_2", image4, Set.of(image5, image6),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);

                var itemDTO = new CreateItemDTO(null, null, 0, print.getId());

                mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(itemDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldUpdateItem() throws Exception {
                var image7 = new Image("name_test_7", "url_test_7");
                var image8 = new Image("name_test_8", "url_test_8");
                var image9 = new Image("name_test_9", "url_test_9");

                var model = modelService.createModel(new CreateModelDTO("model_3", "ref_3", "model_3",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_3", "ref_test_3", image7, Set.of(image8, image9),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                var itemDTO = new CreateItemDTO(Size.G, color, 10, printId);
                itemService.createItem(itemDTO);

                var updateItemDTO = new UpdateItemDTO(Size.GG, color, 15, printId);

                mockMvc.perform(put(
                                "/items/" + printId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateItemDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isNotEmpty())
                                .andExpect(jsonPath("$.size").value("GG"))
                                .andExpect(jsonPath("$.color.name").value("color"))
                                .andExpect(jsonPath("$.availableQuantity").value(15))
                                .andExpect(jsonPath("$.printDTO.id").value(printId));
        }

        @Test
        void shouldReturnBadRequestWithInvalidUpdateDTO() throws Exception {
                var image10 = new Image("name_test_10", "url_test_10");
                var image11 = new Image("name_test_11", "url_test_11");
                var image12 = new Image("name_test_12", "url_test_12");

                var model = modelService.createModel(new CreateModelDTO("model_4", "ref_4", "model_4",
                                BigDecimal.valueOf(12), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_4", "ref_test_4", image10, Set.of(image11, image12),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);
                var printId = print.getId();

                var itemDTO = new CreateItemDTO(Size.G, color, 10, printId);
                itemService.createItem(itemDTO);

                var updateItemDTO = new UpdateItemDTO(null, null, 0, null);

                mockMvc.perform(put(
                                "/items/" + printId).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateItemDTO)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentModel() throws Exception {
                var itemDTO = new CreateItemDTO(Size.G, color, 10, 999L);

                mockMvc.perform(put(
                                "/items/" + 999).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(itemDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeleteItem() throws Exception {
                var image13 = new Image("name_test_13", "url_test_13");
                var image14 = new Image("name_test_14", "url_test_14");
                var image15 = new Image("name_test_15", "url_test_15");

                var model = modelService.createModel(new CreateModelDTO("model_5", "ref_5", "model_5",
                                BigDecimal.valueOf(15), Set.of(Size.G, Size.GG)));

                var printDTO = new CreatePrintDTO(
                                "name_test_5", "ref_test_5", image13, Set.of(image14, image15),
                                Set.of(color), model.getId());

                var print = printService.createPrint(printDTO);

                var itemDTO = new CreateItemDTO(Size.G, color, 10, print.getId());
                var item = itemService.createItem(itemDTO);

                mockMvc.perform(delete("/items/" + item.getId()).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentItem() throws Exception {
                var itemId = 999L;

                mockMvc.perform(delete("/items/" + itemId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

}
