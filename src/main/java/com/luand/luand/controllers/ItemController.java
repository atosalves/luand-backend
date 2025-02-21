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

    @Operation(summary = "Retorna todos os itens", description = "Retorna uma lista com todos os itens cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens recuperada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ItemDetailsDTO>> getAllItems() {
        var result = itemService.getAllItems().stream().map(item -> new ItemDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Cria um novo item", description = "Cria um novo item no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
    })
    @PostMapping
    public ResponseEntity<ItemDetailsDTO> createItem(@RequestBody @Valid CreateItemDTO data) {
        var item = itemService.createItem(data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Atualiza um item", description = "Atualiza um item no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> updateItem(
            @Parameter(description = "ID para atualizar o item") @PathVariable Long id,
            @RequestBody @Valid UpdateItemDTO data) {
        var item = itemService.updateItem(id, data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @Operation(summary = "Deleta um item", description = "Deleta um item no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@Parameter(description = "ID para deletar o item") @PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
