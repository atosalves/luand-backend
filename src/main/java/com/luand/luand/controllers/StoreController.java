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

import com.luand.luand.entities.dto.store.CreateStoreDTO;
import com.luand.luand.entities.dto.store.StoreDetailsDTO;
import com.luand.luand.entities.dto.store.UpdateStoreDTO;
import com.luand.luand.services.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "Retrieve a store by ID", description = "Fetch the details of a specific store using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Store not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StoreDetailsDTO> getStoreById(
            @Parameter(description = "ID of the store to retrieve") @PathVariable Long id) {
        var store = storeService.getStoreById(id);
        return ResponseEntity.ok(new StoreDetailsDTO(store));
    }

    @Operation(summary = "Retrieve a store by name", description = "Fetch the details of a specific store using its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Store not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<StoreDetailsDTO> getStoreByName(
            @Parameter(description = "Name of the store to retrieve") @PathVariable String name) {
        var store = storeService.getStoreByName(name);
        return ResponseEntity.ok(new StoreDetailsDTO(store));
    }

    @Operation(summary = "Retrieve all stores", description = "Fetch a list of all stores.")
    @ApiResponse(responseCode = "200", description = "List of stores retrieved successfully")
    @GetMapping
    public ResponseEntity<List<StoreDetailsDTO>> getAllStores() {
        var result = storeService.getAllStores().stream().map(store -> new StoreDetailsDTO(store))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create a new store", description = "Add a new store to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Name is already in use")
    })
    @PostMapping
    public ResponseEntity<StoreDetailsDTO> createStore(@RequestBody @Valid CreateStoreDTO data) {
        var store = storeService.createStore(data);
        return ResponseEntity.ok(new StoreDetailsDTO(store));
    }

    @Operation(summary = "Update a store", description = "Update the details of an existing store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Store not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Name is already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StoreDetailsDTO> updateStore(
            @Parameter(description = "ID of the store to update") @PathVariable Long id,
            @RequestBody @Valid UpdateStoreDTO data) {
        var store = storeService.updateStore(id, data);
        return ResponseEntity.ok(new StoreDetailsDTO(store));
    }

    @Operation(summary = "Delete a store", description = "Remove a store from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Store deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Store not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(
            @Parameter(description = "ID of the store to delete") @PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

}
