package com.luand.luand.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.UpdateUserDTO;
import com.luand.luand.exceptions.custom_exception.user.UserNotFoundException;
import com.luand.luand.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public User createUser(CreateUserDTO data) {
        var user = new User(data);
        user.setPassword(passwordEncoder.encode(data.password()));

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserDTO data) {
        var user = getUserById(id);

        user.setEmail(data.email());
        user.setName(data.name());
        user.setPassword(passwordEncoder.encode(data.password()));

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
