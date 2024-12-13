package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Size;

public record CreateItemDTO(Size size, String color, int availableQuantity, Long fashionLineId) {

}
