package com.luand.luand.entities.dto.print;

import com.luand.luand.entities.Image;
import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.model.ModelSummaryDTO;

public record PrintSummaryDTO(
                Long id,
                String name,
                Image coverImage,
                ModelSummaryDTO modelDetailsDTO) {
        public PrintSummaryDTO(Print print) {
                this(
                                print.getId(),
                                print.getName(),
                                print.getCoverImage(),
                                new ModelSummaryDTO(print.getModel()));
        }
}
