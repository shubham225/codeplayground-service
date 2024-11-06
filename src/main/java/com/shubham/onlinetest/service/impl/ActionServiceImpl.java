package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.exception.UserProblemNotFoundException;
import com.shubham.onlinetest.model.dto.ActionDTO;
import com.shubham.onlinetest.model.dto.ExecuteReqDTO;
import com.shubham.onlinetest.model.dto.SubmitReqDTO;
import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.entity.User;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.ProblemStatus;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.repository.SubmissionRepository;
import com.shubham.onlinetest.service.ActionService;
import com.shubham.onlinetest.service.UserProblemsService;
import com.shubham.onlinetest.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    private final UserProblemsService userProblemsService;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;

    public ActionServiceImpl(UserProblemsService userProblemsService, UserService userService, SubmissionRepository submissionRepository) {
        this.userProblemsService = userProblemsService;
        this.userService = userService;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username) {
        User user = userService.getUserByUsername(username);
        UserProblem userProblem = null;

        try {
            userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                    user.getId(),
                    submitRequest.getProblemId()
            );
        }catch (UserProblemNotFoundException e) {
            userProblem = new UserProblem();
            userProblem.setUserId(user.getId());
            userProblem.setProblemId(submitRequest.getProblemId());
            userProblem.setStatus(ProblemStatus.PENDING);
            userProblem = userProblemsService.saveUserProblem(userProblem);
        }

        Submission submission = new Submission();
        submission.setCode(submitRequest.getCode());
        submission.setLanguage(submitRequest.getLanguage());

        //TODO: Compile Code and return output
        submission.setStatus(SubmissionStatus.COMPILED);

        submissionRepository.save(submission);
        userProblem.getSubmissions().add(submission);
        userProblem = userProblemsService.saveUserProblem(userProblem);

        return new ActionDTO(submission.getId(), submission.getStatus(), "Compiled",null);
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        //TODO: Implementation
        return null;
    }
}
