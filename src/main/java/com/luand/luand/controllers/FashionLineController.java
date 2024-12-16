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

@RestController
@RequestMapping("/fashion-line")
public class FashionLineController {

    private final FashionLineService fashionLineService;

    public FashionLineController(FashionLineService fashionLineService) {
        this.fashionLineService = fashionLineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FashionLineDetailsDTO> getFashionLineById(@PathVariable Long id) {
        var fashionLine = fashionLineService.getFashionLineById(id);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @GetMapping("/{print}")
    public ResponseEntity<FashionLineDetailsDTO> getFashionLineByPrint(@PathVariable String print) {
        var fashionLine = fashionLineService.getFashionLineByPrint(print);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @GetMapping
    public ResponseEntity<List<FashionLineDetailsDTO>> getAllFashionLines() {
        var result = fashionLineService.getAllFashionLines().stream().map(item -> new FashionLineDetailsDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<FashionLineDetailsDTO> createFashionLine(@RequestBody CreateFashionLineDTO data) {
        var fashionLine = fashionLineService.createFashionLine(data);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FashionLineDetailsDTO> updateFashionLine(@PathVariable Long id, UpdateFashionLineDTO data) {
        var fashionLine = fashionLineService.updateFashionLine(id, data);
        return ResponseEntity.ok(new FashionLineDetailsDTO(fashionLine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFashionLine(@PathVariable Long id) {
        fashionLineService.deleteFashionLine(id);
        return ResponseEntity.noContent().build();
    }
}
