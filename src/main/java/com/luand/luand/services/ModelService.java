package com.luand.luand.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Model;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.model.UpdateModelDTO;
import com.luand.luand.exceptions.custom_exception.model.ModelNotFoundException;
import com.luand.luand.repositories.ModelRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    @Transactional(readOnly = true)
    protected Model getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException());
    }

    @Transactional(readOnly = true)
    public List<Model> getAllModels() {
        var result = modelRepository.findAll();
        return result;
    }

    @Transactional
    public Model createModel(CreateModelDTO data) {
        var model = new Model(data);

        return modelRepository.save(model);
    }

    @Transactional
    public Model updateModel(Long id, UpdateModelDTO data) {
        var model = getModelById(id);

        BeanUtils.copyProperties(data, model);

        return modelRepository.save(model);
    }

    @Transactional
    public void deleteModel(Long id) {
        getModelById(id);
        modelRepository.deleteById(id);
    }

}
