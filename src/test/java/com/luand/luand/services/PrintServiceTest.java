package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;
import com.luand.luand.entities.Model;
import com.luand.luand.entities.Print;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.entities.dto.print.UpdatePrintDTO;
import com.luand.luand.exceptions.custom_exception.print.PrintNotFoundException;
import com.luand.luand.repositories.ColorRepository;
import com.luand.luand.repositories.ImageRepository;
import com.luand.luand.repositories.PrintRepository;

@SpringBootTest
public class PrintServiceTest {
        @MockitoBean
        ModelService modelService;

        @MockitoBean
        PrintRepository printRepository;

        @MockitoBean
        ImageRepository imageRepository;

        @MockitoBean
        ColorRepository colorRepository;

        @Autowired
        PrintService printService;

        List<Print> printEntities;

        @BeforeEach
        void setUp() {
                var image1 = new Image("name_key_1", "url_1");
                image1.setId(1L);
                var image2 = new Image("name_key_2", "url_2");
                image2.setId(2L);
                var image3 = new Image("name_key_3", "url_3");
                image3.setId(3L);
                var image4 = new Image("name_key_4", "url_4");
                image4.setId(4L);
                var image5 = new Image("name_key_5", "url_5");
                image5.setId(5L);
                var image6 = new Image("name_key_6", "url_6");
                image6.setId(6L);
                var image7 = new Image("name_key_7", "url_7");
                image7.setId(7L);

                var color = new Color();
                color.setId(1L);

                var createModelDTO = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);
                model.setId(1L);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, new HashSet<>(Set.of(image1, image2)),
                                Set.of(color), model.getId());
                var createPrintDTO2 = new CreatePrintDTO(
                                "name_test_2", "ref_test_2", image3, new HashSet<>(Set.of(image3, image4)),
                                Set.of(color), model.getId());
                var createPrintDTO3 = new CreatePrintDTO(
                                "name_test_3", "ref_test_3", image5, new HashSet<>(Set.of(image6, image7)),
                                Set.of(color), model.getId());

                var print1 = new Print(createPrintDTO1, model);
                var print2 = new Print(createPrintDTO2, model);
                var print3 = new Print(createPrintDTO3, model);

                printEntities = new ArrayList<>(List.of(print1, print2, print3));
        }

        @AfterEach
        void tearDown() {
                printEntities.clear();
        }

        @Test
        void shouldReturnPrintById() {
                var print = printEntities.get(0);

                var printId = print.getId();

                when(printRepository.findById(printId)).thenReturn(Optional.of(print));

                var result = printService.getPrintById(printId);

                assertNotNull(result);

                assertEquals(printId, result.getId());
                assertEquals(print.getName(), result.getName());
                assertEquals(print.getRef(), result.getRef());
                assertEquals(print.getCoverImage(), result.getCoverImage());
                assertEquals(print.getColors(), result.getColors());
                assertEquals(print.getItems(), result.getItems());
                assertEquals(print.getModel(), result.getModel());

                verify(printRepository).findById(printId);
        }

        @Test
        void shouldThrowNotFoundPrintWhenPrintById() {
                var printId = 1L;

                when(printRepository.findById(printId)).thenReturn(Optional.empty());

                assertThrows(PrintNotFoundException.class, () -> printService.getPrintById(printId));

                verify(printRepository).findById(printId);
        }

        @Test
        void shouldReturnAllPrints() {
                var print1 = printEntities.get(0);
                var print2 = printEntities.get(1);
                var print3 = printEntities.get(2);

                when(printRepository.findAll()).thenReturn(printEntities);

                var result = printService.getAllPrints();

                assertNotNull(result);
                assertEquals(3, result.size());

                assertEquals(print1.getId(), result.get(0).getId());
                assertEquals(print2.getId(), result.get(1).getId());
                assertEquals(print3.getId(), result.get(2).getId());

                verify(printRepository).findAll();
        }

        @Test
        void shouldCreatePrint() {
                var image8 = new Image("name_key_9", "url_9");
                image8.setId(8L);
                var image9 = new Image("name_key_10", "url_10");
                image9.setId(9L);

                var color = new Color();
                color.setId(1L);

                var createModelDTO = new CreateModelDTO(
                                "name_test_4",
                                "ref_test_4",
                                "description_test_4",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));

                var model = new Model(createModelDTO);
                var modelId = 1L;
                model.setId(modelId);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_4", "ref_test_4", image8, Set.of(image8, image9),
                                Set.of(color), modelId);

                when(modelService.getModelById(modelId)).thenReturn(model);
                when(imageRepository.save(image8)).thenAnswer(invoker -> invoker.getArgument(0));
                when(imageRepository.save(image9)).thenAnswer(invoker -> invoker.getArgument(0));
                when(colorRepository.save(color)).thenAnswer(invoker -> invoker.getArgument(0));
                when(printRepository.save(any(Print.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var result = printService.createPrint(createPrintDTO1);

                assertNotNull(result);

                assertEquals(createPrintDTO1.name(), result.getName());
                assertEquals(createPrintDTO1.ref(), result.getRef());
                assertEquals(createPrintDTO1.coverImage(), result.getCoverImage());
                assertEquals(createPrintDTO1.colors(), result.getColors());
                assertEquals(createPrintDTO1.modelId(), result.getModel().getId());

                verify(modelService).getModelById(modelId);
                verify(printRepository).save(result);
        }

        @Test
        void shouldUpdatePrint() {
                var print = printEntities.get(0);
                var printId = print.getId();
                var modelId = print.getModel().getId();

                var originalName = print.getName();
                var originalRef = print.getRef();

                when(printRepository.findById(printId)).thenReturn(Optional.of(print));
                when(modelService.getModelById(modelId)).thenReturn(print.getModel());
                print.getColors().forEach(
                                color -> when(colorRepository.findByHexColor(color.getHexColor())
                                                .or(() -> colorRepository.findByName(color.getName()))
                                                .or(() -> colorRepository.findByRef((color.getRef())))
                                                .orElseGet(() -> colorRepository.save(color)))
                                                .thenReturn(color));
                when(printRepository.findById(printId)).thenReturn(Optional.of(print));
                when(printRepository.save(any(Print.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var updatePrintDTO = new UpdatePrintDTO(
                                "updated_name_test_1", "updated_ref_test_1", print.getCoverImage(), print.getImages(),
                                print.getColors(), modelId);

                var result = printService.updatePrint(printId, updatePrintDTO);

                assertNotNull(result);

                assertNotEquals(originalName, result.getName());
                assertNotEquals(originalRef, result.getRef());

                assertEquals(updatePrintDTO.name(), result.getName());
                assertEquals(updatePrintDTO.ref(), result.getRef());
                assertEquals(updatePrintDTO.coverImage(), result.getCoverImage());
                assertEquals(updatePrintDTO.colors(), result.getColors());
                assertEquals(updatePrintDTO.modelId(), result.getModel().getId());

                verify(printRepository).findById(print.getId());
                verify(printRepository).save(result);
        }

        @Test
        void shouldDeletePrint() {
                var print = printEntities.get(0);
                var printId = print.getId();

                when(printRepository.findById(printId)).thenReturn(Optional.of(print));

                printService.deletePrint(printId);

                verify(printRepository).findById(printId);
                verify(printRepository).deleteById(printId);
        }
}
