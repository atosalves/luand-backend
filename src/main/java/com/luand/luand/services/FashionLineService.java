package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.FashionLine;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;
import com.luand.luand.repositories.FashionLineRepository;

@Service
public class FashionLineService {

    private final FashionLineRepository fashionLineRepository;
    private final ModelService modelService;

    public FashionLineService(FashionLineRepository fashionLineRepository, ModelService modelService) {
        this.fashionLineRepository = fashionLineRepository;
        this.modelService = modelService;
    }

    public FashionLine getFashionLine(Long id) {
        var fashionLine = fashionLineRepository.findById(id);
        return fashionLine.get();
    }

    public List<FashionLine> getAllFashionLines() {
        var result = fashionLineRepository.findAll();
        return result;
    }

    public FashionLine createFashionLine(CreateFashionLineDTO fashionLineDTO) {
        var model = modelService.getModel(fashionLineDTO.modelId());

        var fashionLine = new FashionLine(fashionLineDTO, model);

        return fashionLineRepository.save(fashionLine);
    }

    protected void updateDistincts(FashionLine fashionLine, String color, Size size) {
        fashionLine.getColorsDistinct().add(color);
        fashionLine.getSizesDistinct().add(size);

        fashionLineRepository.save(fashionLine);
    }

}
