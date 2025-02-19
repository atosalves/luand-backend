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
@RequestMapping("/fashion-lines")
@AllArgsConstructor
public class PrintController {

        private final PrintService fashionLineService;

        @Operation(summary = "Retrieve all prints", description = "Fetch a list of all prints.")
        @ApiResponse(responseCode = "200", description = "List of prints retrieved successfully")
        @GetMapping
        public ResponseEntity<List<PrintDetailsDTO>> getAllPrints() {
                var result = fashionLineService.getAllPrints().stream()
                                .map(item -> new PrintDetailsDTO(item))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(result);
        }

        @Operation(summary = "Create a new print", description = "Add a new print to the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Fashion line created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrintDetailsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "409", description = "Print is already in use")
        })
        @PostMapping
        public ResponseEntity<PrintDetailsDTO> createPrint(@RequestBody @Valid CreatePrintDTO data) {
                var fashionLine = fashionLineService.createPrint(data);
                return ResponseEntity.ok(new PrintDetailsDTO(fashionLine));
        }

        @Operation(summary = "Update a print", description = "Update the details of an existing print.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Fashion line updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrintDetailsDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Fashion line not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "409", description = "Print is already in use")
        })
        @PutMapping("/{id}")
        public ResponseEntity<PrintDetailsDTO> updatePrint(
                        @Parameter(description = "ID of the print to update") @PathVariable Long id,
                        @RequestBody @Valid UpdatePrintDTO data) {
                var fashionLine = fashionLineService.updatePrint(id, data);
                return ResponseEntity.ok(new PrintDetailsDTO(fashionLine));
        }

        @Operation(summary = "Delete a print", description = "Remove a print from the system by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Fashion line deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Fashion line not found")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePrint(
                        @Parameter(description = "ID of the print to delete") @PathVariable Long id) {
                fashionLineService.deleteFashionLine(id);
                return ResponseEntity.noContent().build();
        }
}
