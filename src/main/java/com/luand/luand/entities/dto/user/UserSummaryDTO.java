package com.luand.luand.entities.dto.user;

import com.luand.luand.entities.User;

public record UserSummaryDTO(Long id, String username, String email) {
    public UserSummaryDTO(User user) {
        this(user.getId(), user.getUsername(), user.getEmail());
    }
}
