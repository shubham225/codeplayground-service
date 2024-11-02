package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.repository.UserProblemsRepository;
import com.shubham.onlinetest.service.UserProblemsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProblemsServiceImpl implements UserProblemsService {
    private final UserProblemsRepository userProblemsRepository;

    public UserProblemsServiceImpl(UserProblemsRepository userProblemsRepository) {
        this.userProblemsRepository = userProblemsRepository;
    }

    @Override
    public UserProblem getUserProblemByUserAndProblemID(UUID userId, UUID problemID) {
        Optional<UserProblem> userProblemOptional = userProblemsRepository.findByUserIdAndProblemId(userId, problemID);

        return userProblemOptional.orElseGet(UserProblem::new);
    }
}
