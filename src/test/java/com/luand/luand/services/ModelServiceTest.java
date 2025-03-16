package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.exceptions.custom_exception.model.ModelNotFoundException;
import com.luand.luand.repositories.ModelRepository;

@SpringBootTest
public class ModelServiceTest {

        @MockitoBean
        ModelRepository modelRepository;

        @Autowired
        ModelService modelService;

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

                modelEntities = List.of(model1, model2, model3);
        }

        @Test
        void shouldReturnModelById() {
                var model = modelEntities.get(0);
                var modelId = model.getId();

                when(modelRepository.findById(modelId)).thenAnswer(invoker -> invoker.getArgument(0));

                var result = modelService.getModelById(modelId);

                assertNotNull(result);

                assertEquals(modelId, result.getId());
                assertEquals(model.getName(), result.getName());
                assertEquals(model.getRef(), result.getRef());
                assertEquals(model.getDescription(), result.getDescription());
                assertEquals(model.getPrice(), result.getPrice());
                assertEquals(model.getSupportedSizes(), result.getSupportedSizes());

                verify(modelRepository).findById(modelId);
        }

        @Test
        void shouldThrowNotFoundModelWhenModelById() {
                var modelId = 1L;

                when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

                assertThrows(ModelNotFoundException.class, () -> {
                        modelService.getModelById(modelId);
                });

                verify(modelRepository).findById(modelId);
        }

        @Test
        void shouldReturnAllModels() {

                var model = modelEntities.get(0);

                when(modelRepository.findAll()).thenReturn(modelEntities);

                var result = modelService.getAllModels();

                assertNotNull(result);
                assertEquals(modelEntities.size(), result.size());
                assertEquals(model.getName(), result.get(0).getName());

                verify(modelRepository).findAll();
        }

        @Test
        void shouldReturnAllModelsEmpty() {

                var result = modelService.getAllModels();

                assertNotNull(result);

                assertEquals(0, result.size());

                verify(modelRepository).findAll();
        }

        @Test
        void shouldCreateModel() {
                var createModelDTO = new CreateModelDTO(
                                "name_test",
                                "ref_test",
                                "description_test",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));

                var model = new Model(createModelDTO);

                when(modelRepository.save(any(Model.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var result = modelService.createModel(createModelDTO);

                assertNotNull(result);

                assertEquals(model.getName(), result.getName());
                assertEquals(model.getRef(), result.getRef());
                assertEquals(model.getDescription(), result.getDescription());
                assertEquals(model.getPrice(), result.getPrice());
                assertEquals(model.getSupportedSizes(), result.getSupportedSizes());

                verify(modelRepository).save(model);
        }

        @Test
        void shouldUpdateModel() {
                var model = modelEntities.get(0);
                var modelId = model.getId();

                when(modelRepository.findById(modelId)).thenReturn(Optional.of(model));
                when(modelRepository.save(any(Model.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var originalName = model.getName();
                var originalSizes = model.getSupportedSizes();

                var updateModelDTO = new UpdateModelDTO(
                                "name_test_updated",
                                "ref_test_updated",
                                "description_test_updated",
                                BigDecimal.valueOf(20),
                                Set.of(Size.GG));

                var result = modelService.updateModel(modelId, updateModelDTO);

                assertNotNull(result);

                assertNotEquals(originalName, result.getName());
                assertNotEquals(originalSizes, result.getSupportedSizes());

                assertEquals(updateModelDTO.name(), result.getName());
                assertEquals(updateModelDTO.supportedSizes(), result.getSupportedSizes());

                verify(modelRepository).findById(modelId);
                verify(modelRepository).save(model);
        }

        @Test
        void shouldDeleteModel() {
                var model = modelEntities.get(0);
                var modelId = model.getId();

                when(modelRepository.findById(modelId)).thenReturn(Optional.of(model));
                doNothing().when(modelRepository).deleteById(modelId);

                modelService.deleteModel(modelId);

                verify(modelRepository).findById(modelId);
                verify(modelRepository).deleteById(modelId);
        }
}
