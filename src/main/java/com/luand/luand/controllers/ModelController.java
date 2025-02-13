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

        private final ModelService modelService;

        @Operation(summary = "Retrieve all models", description = "Fetch a list of all models.")
        @ApiResponse(responseCode = "200", description = "List of models retrieved successfully")
        @GetMapping
        public ResponseEntity<List<ModelDetailsDTO>> getAllModels() {
                var result = modelService.getAllModels().stream().map(model -> new ModelDetailsDTO(model))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(result);
        }

        @Operation(summary = "Create a new model", description = "Add a new model to the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Model created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "409", description = "Name is already in use")
        })
        @PostMapping
        public ResponseEntity<ModelDetailsDTO> createModel(@RequestBody @Valid CreateModelDTO data) {
                var model = modelService.createModel(data);
                return ResponseEntity.ok(new ModelDetailsDTO(model));
        }

        @Operation(summary = "Update a model", description = "Update the details of an existing model.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Model updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelDetailsDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Model not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "409", description = "Name is already in use")
        })
        @PutMapping("/{id}")
        public ResponseEntity<ModelDetailsDTO> updateModel(
                        @Parameter(description = "ID of the model to update") @PathVariable Long id,
                        @RequestBody @Valid UpdateModelDTO data) {
                var model = modelService.updateModel(id, data);
                return ResponseEntity.ok(new ModelDetailsDTO(model));
        }

        @Operation(summary = "Delete a model", description = "Remove a model from the system by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Model deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Model not found")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteModel(
                        @Parameter(description = "ID of the model to delete") @PathVariable Long id) {
                modelService.deleteModel(id);
                return ResponseEntity.noContent().build();
        }

}
