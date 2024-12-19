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

import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;
import com.luand.luand.entities.dto.fashionLine.FashionLineDetailsDTO;
import com.luand.luand.entities.dto.fashionLine.UpdateFashionLineDTO;
import com.luand.luand.services.FashionLineService;

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
public class FashionLineController {

    private final FashionLineService fashionLineService;

    @Operation(summary = "Retrieve a fashion line by ID", description = "Fetch details of a specific fashion line using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fashion line retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FashionLineDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fashion line not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FashionLineDetailsDTO> getFashionLineById(
            @Parameter(description = "ID of the fashion line to retrieve") @PathVariable Long id) {
        var fashionLine = fashionLineService.getFashionLineById(id);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @Operation(summary = "Retrieve a fashion line by print", description = "Fetch details of a fashion line using its print.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fashion line retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FashionLineDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fashion line not found")
    })
    @GetMapping("/prints/{print}")
    public ResponseEntity<FashionLineDetailsDTO> getFashionLineByPrint(
            @Parameter(description = "Print of the fashion line to retrieve") @PathVariable String print) {
        var fashionLine = fashionLineService.getFashionLineByPrint(print);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @Operation(summary = "Retrieve all fashion lines", description = "Fetch a list of all fashion lines.")
    @ApiResponse(responseCode = "200", description = "List of fashion lines retrieved successfully")
    @GetMapping
    public ResponseEntity<List<FashionLineDetailsDTO>> getAllFashionLines() {
        var result = fashionLineService.getAllFashionLines().stream().map(item -> new FashionLineDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create a new fashion line", description = "Add a new fashion line to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fashion line created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FashionLineDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Print is already in use")
    })
    @PostMapping
    public ResponseEntity<FashionLineDetailsDTO> createFashionLine(@RequestBody @Valid CreateFashionLineDTO data) {
        var fashionLine = fashionLineService.createFashionLine(data);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @Operation(summary = "Update a fashion line", description = "Update the details of an existing fashion line.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fashion line updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FashionLineDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fashion line not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Print is already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FashionLineDetailsDTO> updateFashionLine(
            @Parameter(description = "ID of the fashion line to update") @PathVariable Long id,
            @RequestBody @Valid UpdateFashionLineDTO data) {
        var fashionLine = fashionLineService.updateFashionLine(id, data);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @Operation(summary = "Delete a fashion line", description = "Remove a fashion line from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fashion line deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fashion line not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFashionLine(
            @Parameter(description = "ID of the fashion line to delete") @PathVariable Long id) {
        fashionLineService.deleteFashionLine(id);
        return ResponseEntity.noContent().build();
    }
}
