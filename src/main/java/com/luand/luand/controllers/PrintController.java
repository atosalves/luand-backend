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

import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.entities.dto.print.PrintDetailsDTO;
import com.luand.luand.entities.dto.print.UpdatePrintDTO;
import com.luand.luand.services.PrintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/prints")
@AllArgsConstructor
public class PrintController {

        private final PrintService printService;

        @Operation(summary = "Retorna todas as estampas", description = "Retorna uma lista com todas as estampas cadastradas no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de estampas recuperada com sucesso")
        })
        @GetMapping
        public ResponseEntity<List<PrintDetailsDTO>> getAllPrints() {
                var result = printService.getAllPrints().stream()
                                .map(item -> new PrintDetailsDTO(item))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(result);
        }

        @Operation(summary = "Cria uma nova estampa", description = "Cria uma nova estampa no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Estampa criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrintDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
        })
        @PostMapping
        public ResponseEntity<PrintDetailsDTO> createPrint(@RequestBody @Valid CreatePrintDTO data) {
                var print = printService.createPrint(data);
                return ResponseEntity.ok(new PrintDetailsDTO(print));
        }

        @Operation(summary = "Atualiza uma estampa", description = "Atualiza uma estampa no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Estampa atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrintDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                        @ApiResponse(responseCode = "404", description = "Estampa não encontrada")
        })
        @PutMapping("/{id}")
        public ResponseEntity<PrintDetailsDTO> updatePrint(
                        @Parameter(description = "ID para atualizar a estampa") @PathVariable Long id,
                        @RequestBody @Valid UpdatePrintDTO data) {
                var print = printService.updatePrint(id, data);
                return ResponseEntity.ok(new PrintDetailsDTO(print));
        }

        @Operation(summary = "Deleta uma estampa", description = "Deleta uma estampa no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Estampa deletada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Estampa não encontrada")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePrint(
                        @Parameter(description = "ID para deletar a estampa") @PathVariable Long id) {
                printService.deletePrint(id);
                return ResponseEntity.noContent().build();
        }
}
