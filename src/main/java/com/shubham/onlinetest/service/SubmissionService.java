package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.entity.Submission;

import java.util.Set;
import java.util.UUID;

public interface SubmissionService {
    public Set<Submission> getAllSubmissionsByUserAndProblemId(UUID userId, UUID problemId);
}
