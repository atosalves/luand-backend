package com.luand.luand.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.exception.model.ModelAlreadyExistsException;
import com.luand.luand.exception.model.ModelNotFoundException;
import com.luand.luand.repositories.ModelRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    @Transactional(readOnly = true)
<<<<<<< HEAD
    protected Model getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));
=======
    public Model getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));
    }

    @Transactional(readOnly = true)
    public Model getModelByName(String name) {
        return modelRepository.findByName(name)
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));
>>>>>>> develop
    }

    @Transactional(readOnly = true)
    public List<Model> getAllModels() {
        var result = modelRepository.findAll();
        return result;
    }

    @Transactional
    public Model createModel(CreateModelDTO data) {
<<<<<<< HEAD
        var model = new Model(data);

        var isModelPresent = modelRepository.findByName(model.getName()).isPresent();

        if (isModelPresent) {
            throw new ModelAlreadyExistsException("Name is already in use");
        }

=======
        verifyModelExists(data.name());

        var model = new Model(data);
>>>>>>> develop
        return modelRepository.save(model);
    }

    @Transactional
    public Model updateModel(Long id, UpdateModelDTO data) {
        var model = getModelById(id);

<<<<<<< HEAD
        BeanUtils.copyProperties(data, model);
=======
        verifyModelExists(data.name());

        model.setName(data.name());
        model.setDescription(data.description());
        model.setPrice(data.price());
>>>>>>> develop

        return modelRepository.save(model);
    }

    @Transactional
    public void deleteModel(Long id) {
        getModelById(id);
        modelRepository.deleteById(id);
    }

<<<<<<< HEAD
=======
    private void verifyModelExists(String name) {
        var modelByName = modelRepository.findByName(name);
        if (modelByName.isPresent()) {
            throw new ModelAlreadyExistsException("Name is already in use");
        }
    }

>>>>>>> develop
}
