package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.UserNotFoundException;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.repository.UserRepository;
import com.shubham.codeplayground.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) throw new UserNotFoundException("User with name '" + username + "' not found");

        return user.get();
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("User with id '" + id + "' not found");

        return user.get();
    }
}
