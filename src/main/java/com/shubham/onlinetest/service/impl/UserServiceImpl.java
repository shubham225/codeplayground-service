package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.exception.UserNotFoundException;
import com.shubham.onlinetest.model.entity.User;
import com.shubham.onlinetest.repository.UserRepository;
import com.shubham.onlinetest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
