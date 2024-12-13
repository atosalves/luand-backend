package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.repositories.ModelRepository;

@Service
public class ModelService {

    private ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Model getModel(Long id) {
        var model = modelRepository.findById(id);
        return model.get();
    }

    public List<Model> getAllModels() {
        var result = modelRepository.findAll();
        return result;
    }

    public Model createModel(CreateModelDTO modelDTO) {
        var model = new Model(modelDTO);
        return modelRepository.save(model);
    }

}
