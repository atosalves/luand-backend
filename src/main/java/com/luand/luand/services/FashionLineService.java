package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.FashionLine;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;
import com.luand.luand.entities.dto.fashionLine.UpdateFashionLineDTO;
import com.luand.luand.exception.fashionLine.FashionLineNotFoundException;
import com.luand.luand.exception.fashionLine.FashionLineAlreadyExistsException;
import com.luand.luand.repositories.FashionLineRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FashionLineService {

    private final FashionLineRepository fashionLineRepository;
    private final ModelService modelService;

    @Transactional(readOnly = true)
    public FashionLine getFashionLineById(Long id) {
        return fashionLineRepository.findById(id)
                .orElseThrow(() -> new FashionLineNotFoundException("Fashion Line not found"));
    }

    @Transactional(readOnly = true)
    public FashionLine getFashionLineByPrint(String print) {
        return fashionLineRepository.findByPrint(print)
                .orElseThrow(() -> new FashionLineNotFoundException("Fashion Line not found"));
    }

    @Transactional(readOnly = true)
    public List<FashionLine> getAllFashionLines() {
        var result = fashionLineRepository.findAll();
        return result;
    }

    @Transactional
    public FashionLine createFashionLine(CreateFashionLineDTO data) {
        verifyFashionLinexists(data.print());

        var model = modelService.getModelById(data.modelId());
        var fashionLine = new FashionLine(data, model);

        return fashionLineRepository.save(fashionLine);
    }

    @Transactional
    public FashionLine updateFashionLine(Long id, UpdateFashionLineDTO data) {
        var fashionLine = getFashionLineById(id);

        verifyFashionLinexists(data.print());

        fashionLine.setName(data.name());
        fashionLine.setPrint(data.print());

        var model = modelService.getModelById(data.modelId());
        fashionLine.setModel(model);

        return fashionLineRepository.save(fashionLine);
    }

    @Transactional
    public void deleteFashionLine(Long id) {
        getFashionLineById(id);
        fashionLineRepository.deleteById(id);
    }

    @Transactional
    protected void addDistincts(FashionLine fashionLine, String color, Size size) {
        fashionLine.getColorsDistinct().add(color);
        fashionLine.getSizesDistinct().add(size);

        fashionLineRepository.save(fashionLine);
    }

    @Transactional
    protected void removeDistincts(FashionLine fashionLine, String color, Size size) {
        fashionLine.getColorsDistinct().remove(color);
        fashionLine.getSizesDistinct().remove(size);

        fashionLineRepository.save(fashionLine);
    }

    @Transactional(readOnly = true)
    private void verifyFashionLinexists(String print) {
        var fashionLineByColor = fashionLineRepository.findByPrint(print);
        if (fashionLineByColor.isPresent()) {
            throw new FashionLineAlreadyExistsException("Print is already in use");
        }
    }

}
