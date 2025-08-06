package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.entity.User;

import java.util.UUID;

public interface UserService {
    public User getUserByUsername(String username);
    public User getUserById(UUID id);
}
