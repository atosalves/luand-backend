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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/models")
@AllArgsConstructor
public class ModelController {

        private final ModelService modelService;

        @Operation(summary = "Retorna todos os modelos", description = "Retorna uma lista com todos os modelos cadastrados no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de modelos recuperada com sucesso")
        })
        @GetMapping
        public ResponseEntity<List<ModelDetailsDTO>> getAllModels() {
                var result = modelService.getAllModels().stream().map(model -> new ModelDetailsDTO(model))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(result);
        }

        @Operation(summary = "Cria um novo modelo", description = "Cria um novo modelo no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Modelo criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
        @PostMapping
        public ResponseEntity<ModelDetailsDTO> createModel(@RequestBody @Valid CreateModelDTO data) {
                var model = modelService.createModel(data);
                return ResponseEntity.ok(new ModelDetailsDTO(model));
        }

        @Operation(summary = "Atualiza um modelo", description = "Atualiza um modelo no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Modelo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                        @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
        })
        @PutMapping("/{id}")
        public ResponseEntity<ModelDetailsDTO> updateModel(
                        @Parameter(description = "ID para atualizar o modelo") @PathVariable Long id,
                        @RequestBody @Valid UpdateModelDTO data) {
                var model = modelService.updateModel(id, data);
                return ResponseEntity.ok(new ModelDetailsDTO(model));
        }

        @Operation(summary = "Deleta um modelo", description = "Deleta um modelo no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Modelo deletado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteModel(
                        @Parameter(description = "ID para deletar o modelo") @PathVariable Long id) {
                modelService.deleteModel(id);
                return ResponseEntity.noContent().build();
        }

}
