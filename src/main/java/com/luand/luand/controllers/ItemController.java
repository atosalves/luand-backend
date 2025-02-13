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

import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.ItemDetailsDTO;
import com.luand.luand.entities.dto.item.ItemQuantityUpdateDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.services.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Retrieve an item by ID", description = "Fetch details of a specific item using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> getItemById(
            @Parameter(description = "ID of the item to retrieve") @PathVariable Long id) {
        var item = itemService.getItemById(id);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Retrieve an item by color", description = "Fetch details of an item using its color.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/color/{color}")
    public ResponseEntity<ItemDetailsDTO> getItemByColor(
            @Parameter(description = "Color of the item to retrieve") @PathVariable String color) {
        var item = itemService.getItemByColor(color);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Retrieve all items", description = "Fetch a list of all items.")
    @ApiResponse(responseCode = "200", description = "List of items retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ItemDetailsDTO>> getAllItems() {
        var result = itemService.getAllItems().stream().map(item -> new ItemDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create a new item", description = "Add a new item to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Fashion not found")
    })
    @PostMapping
    public ResponseEntity<ItemDetailsDTO> createItem(@RequestBody @Valid CreateItemDTO data) {
        var item = itemService.createItem(data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Update available quantity of an item", description = "Modify the available quantity of a specific item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available quantity updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Fashion not found")
    })
    @PutMapping("/{id}/available-quantity")
    public ResponseEntity<ItemDetailsDTO> updateAvailableQuantityItem(
            @Parameter(description = "ID of the item to update") @PathVariable Long id,
            @RequestBody @Valid ItemQuantityUpdateDTO data) {
        var item = itemService.updateAvailableQuantityItem(id, data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Update an item", description = "Update the details of an existing item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Fashion not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> updateItem(
            @Parameter(description = "ID of the item to update") @PathVariable Long id,
            @RequestBody @Valid UpdateItemDTO data) {
        var item = itemService.updateItem(id, data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Delete an item", description = "Remove an item from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "404", description = "Fashion not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@Parameter(description = "ID of the item to delete") @PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
