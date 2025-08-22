package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.ActiveProblem;

import java.util.Set;
import java.util.UUID;

public interface ActiveProblemsService {
    ActiveProblem getUserProblemByUserAndProblemID(UUID userId, UUID problemID);
    ActiveProblem getUserProblemByID(UUID id);
    Set<CodeDTO> getLatestUserCode(UUID id);
    Set<Submission> getSubmissionsByUserProblemId(UUID id);
    Set<SubmissionDTO> getSubmissionDTOByUserProblemId(UUID id);
    ActiveProblem saveUserProblem(ActiveProblem activeProblem);
}
