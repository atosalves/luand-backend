package com.luand.luand.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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
import com.luand.luand.repositories.PrintRepository;

@SpringBootTest
public class PrintServiceTest {
        @MockitoBean
        ModelService modelService;

        @MockitoBean
        PrintRepository printRepository;

        @Autowired
        PrintService printService;

        List<Print> printEntities;

        @BeforeEach
        void setUp() {
                var createModelDTO = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", new Image(), Set.of(new Image(), new Image()),
                                Set.of(new Color()), model.getId());
                var createPrintDTO2 = new CreatePrintDTO(
                                "name_test_2", "ref_test_2", new Image(), Set.of(new Image(), new Image()),
                                Set.of(new Color()), model.getId());
                var createPrintDTO3 = new CreatePrintDTO(
                                "name_test_3", "ref_test_3", new Image(), Set.of(new Image(), new Image()),
                                Set.of(new Color()), model.getId());

                var print1 = new Print(createPrintDTO1, model);
                var print2 = new Print(createPrintDTO2, model);
                var print3 = new Print(createPrintDTO3, model);

                printEntities = List.of(print1, print2, print3);
        }

}
