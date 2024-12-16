package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.exception.model.ModelAlreadyExistsException;
import com.luand.luand.exception.model.ModelNotFoundException;
import com.luand.luand.repositories.ModelRepository;

@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Model getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));
    }

    public Model getModelByName(String name) {
        return modelRepository.findByName(name)
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));
    }

    public List<Model> getAllModels() {
        var result = modelRepository.findAll();
        return result;
    }

    public Model createModel(CreateModelDTO data) {
        verifyModelExists(data.name());

        var model = new Model(data);
        return modelRepository.save(model);
    }

    public Model updateModel(Long id, UpdateModelDTO data) {
        var model = getModelById(id);

        verifyModelExists(data.name());

        model.setName(data.name());
        model.setDescription(data.description());
        model.setPrice(data.price());

        return modelRepository.save(model);
    }

    public void deleteModel(Long id) {
        getModelById(id);
        modelRepository.deleteById(id);
    }

    private void verifyModelExists(String name) {
        var modelByName = modelRepository.findByName(name);
        if (modelByName.isPresent()) {
            throw new ModelAlreadyExistsException("Model is already in use");
        }
    }

}
