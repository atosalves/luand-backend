package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.FashionLine;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;
import com.luand.luand.entities.dto.fashionLine.UpdateFashionLineDTO;
import com.luand.luand.exception.fashionLine.FashionLineNotFoundException;
import com.luand.luand.exception.fashionLine.FashionLineAlreadyExistsException;
import com.luand.luand.repositories.FashionLineRepository;

@Service
public class FashionLineService {

    private final FashionLineRepository fashionLineRepository;
    private final ModelService modelService;

    public FashionLineService(FashionLineRepository fashionLineRepository, ModelService modelService) {
        this.fashionLineRepository = fashionLineRepository;
        this.modelService = modelService;
    }

    public FashionLine getFashionLineById(Long id) {
        return fashionLineRepository.findById(id)
                .orElseThrow(() -> new FashionLineNotFoundException("Fashion Line not found"));
    }

    public FashionLine getFashionLineByPrint(String print) {
        return fashionLineRepository.findByPrint(print)
                .orElseThrow(() -> new FashionLineNotFoundException("Fashion Line not found"));
    }

    public List<FashionLine> getAllFashionLines() {
        var result = fashionLineRepository.findAll();
        return result;
    }

    public FashionLine createFashionLine(CreateFashionLineDTO data) {
        verifyFashionLinexists(data.print());

        var model = modelService.getModelById(data.modelId());
        var fashionLine = new FashionLine(data, model);

        return fashionLineRepository.save(fashionLine);
    }

    public FashionLine updateFashionLine(Long id, UpdateFashionLineDTO data) {
        var fashionLine = getFashionLineById(id);

        verifyFashionLinexists(data.print());

        fashionLine.setName(data.name());
        fashionLine.setPrint(data.print());

        var model = modelService.getModelById(data.modelId());
        fashionLine.setModel(model);

        return fashionLineRepository.save(fashionLine);
    }

    public void deleteFashionLine(Long id) {
        getFashionLineById(id);
        fashionLineRepository.deleteById(id);
    }

    protected void updateDistincts(FashionLine fashionLine, String color, Size size) {
        fashionLine.getColorsDistinct().add(color);
        fashionLine.getSizesDistinct().add(size);

        fashionLineRepository.save(fashionLine);
    }

    private void verifyFashionLinexists(String print) {
        var fashionLineByColor = fashionLineRepository.findByPrint(print);
        if (fashionLineByColor.isPresent()) {
            throw new FashionLineAlreadyExistsException("Print is already in use");
        }
    }

}
