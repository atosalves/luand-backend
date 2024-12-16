package com.luand.luand.entities.dto.item;

import com.luand.luand.entities.Size;

public record UpdateItemDTO(String color, Size size, int availableQuantity, Long fashionLineDTO) {

}
