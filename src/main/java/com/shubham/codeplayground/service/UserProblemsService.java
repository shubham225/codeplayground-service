package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.UserProblem;

import java.util.Set;
import java.util.UUID;

public interface UserProblemsService {
    UserProblem getUserProblemByUserAndProblemID(UUID userId, UUID problemID);
    UserProblem getUserProblemByID(UUID id);
    Set<CodeDTO> getLatestUserCode(UUID id);
    Set<Submission> getSubmissionsByUserProblemId(UUID id);
    Set<SubmissionDTO> getSubmissionDTOByUserProblemId(UUID id);
    UserProblem saveUserProblem(UserProblem userProblem);
}
