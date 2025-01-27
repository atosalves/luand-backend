package com.luand.luand.entities.dto.store;

import com.luand.luand.entities.Store;

public record StoreDetailsDTO(Long id, String name, String description) {
    public StoreDetailsDTO(Store store) {
        this(store.getId(), store.getName(), store.getDescription());
    }
}
