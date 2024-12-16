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

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> getItemById(@PathVariable Long id) {
        var item = itemService.getItemById(id);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @GetMapping("/{color}")
    public ResponseEntity<ItemDetailsDTO> getItemByColor(@PathVariable String color) {
        var item = itemService.getItemByColor(color);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @GetMapping
    public ResponseEntity<List<ItemDetailsDTO>> getAllItens() {
        var result = itemService.getAllItens().stream().map(item -> new ItemDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ItemDetailsDTO> createItem(@RequestBody CreateItemDTO data) {
        var item = itemService.createItem(data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> updateItem(@PathVariable Long id, UpdateItemDTO data) {
        var item = itemService.updateItem(id, data);
        return ResponseEntity.ok(new ItemDetailsDTO(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
