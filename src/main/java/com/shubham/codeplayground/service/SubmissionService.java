package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.entity.Submission;

import java.util.Set;
import java.util.UUID;

public interface SubmissionService {
    public Set<Submission> getAllSubmissionsByUserAndProblemId(UUID userId, UUID problemId);
}
