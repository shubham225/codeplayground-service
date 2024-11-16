package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.dto.CodeDTO;
import com.shubham.onlinetest.model.dto.SubmissionDTO;
import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.Language;

import java.util.List;
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
