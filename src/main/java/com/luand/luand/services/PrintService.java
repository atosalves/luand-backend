package com.luand.luand.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Print;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.entities.dto.print.UpdatePrintDTO;
import com.luand.luand.exceptions.custom_exception.print.PrintNotFoundException;
import com.luand.luand.repositories.ColorRepository;
import com.luand.luand.repositories.ImageRepository;
import com.luand.luand.repositories.PrintRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrintService {

    private final PrintRepository printRepository;
    private final ColorRepository colorRepository;
    private final ImageRepository imageRepository;
    private final ModelService modelService;

    @Transactional(readOnly = true)
    public List<Print> getAllPrints() {
        var result = printRepository.findAll();
        return result;
    }

    @Transactional
    public Print createPrint(CreatePrintDTO data) {

        var model = modelService.getModelById(data.modelId());

        var coverImage = imageRepository.save(data.coverImage());

        var images = data.images().stream().map(image -> imageRepository.save(image)).collect(Collectors.toSet());

        var colors = data.colors().stream().map(color -> colorRepository.findByHexColor(color.getHexColor())
                .or(() -> colorRepository.findByName(color.getName()))
                .or(() -> colorRepository.findByRef((color.getRef()))).orElseGet(() -> colorRepository.save(color)))
                .collect(Collectors.toSet());

        var print = new Print(data, model);
        print.setCoverImage(coverImage);
        print.setImages(new HashSet<>(images));
        print.setColors(new HashSet<>(colors));

        return printRepository.save(print);
    }

    @Transactional
    public Print updatePrint(Long id, UpdatePrintDTO data) {
        var print = getPrintById(id);

        var model = modelService.getModelById(data.modelId());
        print.setModel(model);

        print.setName(data.name());
        print.setRef(data.ref());
        print.setCoverImage(data.coverImage());

        print.getImages().clear();
        print.getImages().addAll(data.images());

        print.getColors().stream().map(
                color -> colorRepository.findByHexColor(color.getHexColor())
                        .or(() -> colorRepository.findByName(color.getName()))
                        .or(() -> colorRepository.findByRef((color.getRef())))
                        .orElseGet(() -> colorRepository.save(color)))
                .collect(Collectors.toSet());

        return printRepository.save(print);
    }

    @Transactional
    public void deletePrint(Long id) {
        getPrintById(id);
        printRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    protected Print getPrintById(Long id) {
        return printRepository.findById(id)
                .orElseThrow(() -> new PrintNotFoundException());
    }

}
