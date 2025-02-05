package com.luand.luand.entities.dto.user;

import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.store.StoreDetailsDTO;

public record UserSummaryDTO(Long id, String name, String email, StoreDetailsDTO storeDTO) {
    public UserSummaryDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), new StoreDetailsDTO(user.getStore()));
    }
}
