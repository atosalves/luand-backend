package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Store;
import com.luand.luand.entities.dto.store.CreateStoreDTO;
import com.luand.luand.entities.dto.store.UpdateStoreDTO;
import com.luand.luand.exception.store.StoreNotFoundException;
import com.luand.luand.exception.store.StoreAlreadyExistsException;
import com.luand.luand.repositories.StoreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStores() {
        var result = storeRepository.findAll();
        return result;
    }

    @Transactional(readOnly = true)
    public Store getStoreByName(String name) {
        return storeRepository.findByName(name)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }

    @Transactional
    public Store createStore(CreateStoreDTO data) {
        verifyStoreExists(data.name());

        var store = new Store(data);
        return storeRepository.save(store);
    }

    @Transactional
    public Store updateStore(Long id, UpdateStoreDTO data) {
        var store = getStoreById(id);

        verifyStoreExists(data.name());

        store.setName(data.name());
        store.setDescription(data.description());

        return storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(Long id) {
        getStoreById(id);
        storeRepository.deleteById(id);
    }

    private void verifyStoreExists(String name) {
        var storeByName = storeRepository.findByName(name);
        if (storeByName.isPresent()) {
            throw new StoreAlreadyExistsException("Name is already in use");
        }
    }

}
