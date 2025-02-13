package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Item;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.fashionLine.FashionLineDetailsDTO;

public record ItemDetailsDTO(Long id, String color, Size size, int availableQuantity,
        FashionLineDetailsDTO fashionLineDTO) {

    public ItemDetailsDTO(Item item) {
        this(item.getId(), item.getColor(), item.getSize(), item.getAvailableQuantity(),
                new FashionLineDetailsDTO(item.getFashionLine()));
    }

}
