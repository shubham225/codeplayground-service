package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.ActiveProblem;

import java.util.Set;
import java.util.UUID;

public interface ActiveProblemsService {
    ActiveProblem getActiveProblemByUserAndProblemId(UUID userId, UUID problemID);
    ActiveProblem getActiveProblemById(UUID id);
    Set<CodeDTO> getLatestUserCodeByActiveProblemId(UUID id);
    Set<SubmissionDTO> getSubmissionsByActiveProblemId(UUID id);
    ActiveProblem saveActiveProblem(ActiveProblem activeProblem);
}
