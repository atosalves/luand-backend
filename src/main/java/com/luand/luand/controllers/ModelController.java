package com.luand.luand.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;
import com.luand.luand.services.ModelService;

@RestController
@RequestMapping("/model")
public class ModelController {

    private ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDetailsDTO> getModel(@PathVariable Long id) {
        var model = modelService.getModel(id);
        var result = new ModelDetailsDTO(model);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<ModelDetailsDTO>> getAllModels() {
        var result = modelService.getAllModels().stream().map(model -> new ModelDetailsDTO(model))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<ModelDetailsDTO> createModel(@RequestBody CreateModelDTO modelDTO) {
        var model = modelService.createModel(modelDTO);
        var result = new ModelDetailsDTO(model);

        return ResponseEntity.ok().body(result);
    }

}
