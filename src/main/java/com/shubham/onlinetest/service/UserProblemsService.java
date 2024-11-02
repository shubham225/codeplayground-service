package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.entity.UserProblem;

import java.util.UUID;

public interface UserProblemsService {
    public UserProblem getUserProblemByUserAndProblemID(UUID userId, UUID problemID);
}
