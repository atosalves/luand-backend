package com.luand.luand.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.repositories.ModelRepository;


@SpringBootTest
public class ModelServiceTest {

        @MockitoBean
        ModelRepository modelRepository;
        
        @Autowired
        ModelService modelService;

        @Test
        void shouldCreateModel() {
                var createModelDTO = new CreateModelDTO("name_test", "ref_test", "description_test", BigDecimal.valueOf(10), Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);
                
                when(modelRepository.save(any(Model.class))).thenReturn(model);

                var result = modelService.createModel(createModelDTO);

                assertNotNull(result);
                
                assertEquals("name_test", result.getName());
                assertEquals("ref_test", result.getRef());
                assertEquals("description_test", result.getDescription());
                assertEquals(BigDecimal.valueOf(10), result.getPrice());
                assertEquals(Set.of(Size.P, Size.M), result.getSupportedSizes());

                verify(modelRepository).save(any(Model.class));
        }
}
