package com.luand.luand.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.ModelDetailsDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.services.ModelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDetailsDTO> getModelById(@PathVariable Long id) {
        var model = modelService.getModelById(id);
        return ResponseEntity.ok(new ModelDetailsDTO(model));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ModelDetailsDTO> getModelById(@PathVariable String name) {
        var model = modelService.getModelByName(name);
        return ResponseEntity.ok(new ModelDetailsDTO(model));
    }

    @GetMapping
    public ResponseEntity<List<ModelDetailsDTO>> getAllModels() {
        var result = modelService.getAllModels().stream().map(model -> new ModelDetailsDTO(model))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ModelDetailsDTO> createModel(@RequestBody @Valid CreateModelDTO data) {
        var model = modelService.createModel(data);
        return ResponseEntity.ok(new ModelDetailsDTO(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelDetailsDTO> updateModel(@PathVariable Long id, @RequestBody @Valid UpdateModelDTO data) {
        var model = modelService.updateModel(id, data);
        return ResponseEntity.ok(new ModelDetailsDTO(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        modelService.deleteModel(id);
        return ResponseEntity.noContent().build();
    }

}
