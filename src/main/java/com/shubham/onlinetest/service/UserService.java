package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.entity.User;

public interface UserService {
    public User getUserByUsername(String username);
}
