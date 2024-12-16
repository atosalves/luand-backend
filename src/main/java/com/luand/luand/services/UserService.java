package com.luand.luand.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.User;
import com.luand.luand.entities.dto.user.CreateUserDTO;
import com.luand.luand.entities.dto.user.UpdateUserDTO;
import com.luand.luand.exception.user.UserAlreadyExistsException;
import com.luand.luand.exception.user.UserNotFoundException;
import com.luand.luand.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User createUser(CreateUserDTO data) {
        verifyUserExists(data.email(), data.username());

        var user = new User(data);
        user.setPassword(passwordEncoder.encode(data.password()));

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserDTO data) {
        var user = getUserById(id);

        verifyUserExists(data.email(), data.username());

        user.setEmail(data.email());
        user.setUsername(data.username());
        user.setPassword(passwordEncoder.encode(data.password()));

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    public void validatePassword(String rawPassword, String encodedPassword) {
        var isValid = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!isValid) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Transactional(readOnly = true)
    private void verifyUserExists(String email, String username) {
        var userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new UserAlreadyExistsException("Email is already in use");
        }

        var userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isPresent()) {
            throw new UserAlreadyExistsException("Username is already in use");
        }
    }

}
