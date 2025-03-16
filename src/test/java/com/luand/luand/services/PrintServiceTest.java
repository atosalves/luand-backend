package com.luand.luand.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.repositories.PrintRepository;

@SpringBootTest
public class PrintServiceTest {
        @MockitoBean
        ModelService modelService;

        @MockitoBean
        PrintRepository printRepository;

        @Autowired
        PrintService printService;

}
