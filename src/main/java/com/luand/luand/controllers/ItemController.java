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

import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.ItemDetailsDTO;
import com.luand.luand.services.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> getItem(@PathVariable Long id) {
        var item = itemService.getItem(id);
        var result = new ItemDetailsDTO(item);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<ItemDetailsDTO>> getAllItens() {
        var result = itemService.getAllItens().stream().map(item -> new ItemDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<ItemDetailsDTO> createItem(@RequestBody CreateItemDTO itemDTO) {
        var item = itemService.createItem(itemDTO);
        var result = new ItemDetailsDTO(item);

        return ResponseEntity.ok().body(result);
    }
}
