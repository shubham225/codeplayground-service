package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.entity.User;

import java.util.UUID;

public interface UserService {
    public User getUserByUsername(String username);
    public User getUserById(UUID id);
}
