package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.entities.dto.print.UpdatePrintDTO;
import com.luand.luand.exception.print.PrintNotFoundException;
import com.luand.luand.repositories.PrintRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrintService {

    private final PrintRepository printRepository;
    private final ModelService modelService;

    @Transactional(readOnly = true)
    public List<Print> getAllPrints() {
        var result = printRepository.findAll();
        return result;
    }

    @Transactional
    public Print createPrint(CreatePrintDTO data) {

        var model = modelService.getModelById(data.modelId());
        var print = new Print(data, model);

        return printRepository.save(print);
    }

    @Transactional
    public Print updatePrint(Long id, UpdatePrintDTO data) {
        var print = getPrintById(id);

        print.setName(data.name());
        print.setImage(data.print());

        var model = modelService.getModelById(data.modelId());
        print.setModel(model);

        return printRepository.save(print);
    }

    @Transactional
    public void deleteFashionLine(Long id) {
        getPrintById(id);
        printRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    protected Print getPrintById(Long id) {
        return printRepository.findById(id)
                .orElseThrow(() -> new PrintNotFoundException("Print not found"));
    }

}
