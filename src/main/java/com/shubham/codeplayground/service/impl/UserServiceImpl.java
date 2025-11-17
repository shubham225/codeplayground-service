package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.UserNotFoundException;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.repository.UserRepository;
import com.shubham.codeplayground.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(
                        MessageFormat.format("User with name {0} not found", username))
        );
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(
                        MessageFormat.format("User with id {0} not found", id))
        );
    }
}
