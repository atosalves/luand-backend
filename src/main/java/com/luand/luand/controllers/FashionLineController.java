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

import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;
import com.luand.luand.entities.dto.fashionLine.FashionLineDetailsDTO;
import com.luand.luand.services.FashionLineService;

@RestController
@RequestMapping("/fashion-line")
public class FashionLineController {

    private final FashionLineService fashionLineService;

    public FashionLineController(FashionLineService fashionLineService) {
        this.fashionLineService = fashionLineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FashionLineDetailsDTO> getFashionLine(@PathVariable Long id) {
        var fashionLine = fashionLineService.getFashionLine(id);
        var result = new FashionLineDetailsDTO(fashionLine);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<FashionLineDetailsDTO>> getAllItens() {
        var result = fashionLineService.getAllFashionLines().stream().map(item -> new FashionLineDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<FashionLineDetailsDTO> createFashionLine(@RequestBody CreateFashionLineDTO fashionLineDTO) {
        var fashionLine = fashionLineService.createFashionLine(fashionLineDTO);
        var result = new FashionLineDetailsDTO(fashionLine);

        return ResponseEntity.ok().body(result);
    }
}
