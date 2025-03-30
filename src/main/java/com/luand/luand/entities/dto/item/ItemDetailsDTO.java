package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Item;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.print.PrintSummaryDTO;

public record ItemDetailsDTO(Long id, Color color, Size size, int availableQuantity,
        PrintSummaryDTO printDTO) {

    public ItemDetailsDTO(Item item) {
        this(item.getId(), item.getColor(), item.getSize(), item.getAvailableQuantity(),
                new PrintSummaryDTO(item.getPrint()));
    }

}
