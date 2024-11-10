package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.exception.SubmissionNotFoundException;
import com.shubham.onlinetest.exception.UserProblemNotFoundException;
import com.shubham.onlinetest.model.dto.ActionDTO;
import com.shubham.onlinetest.model.dto.ExecuteReqDTO;
import com.shubham.onlinetest.model.dto.SubmissionDTO;
import com.shubham.onlinetest.model.dto.SubmitReqDTO;
import com.shubham.onlinetest.model.entity.Problem;
import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.entity.User;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.ProblemStatus;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.model.mapper.SubmissionMapper;
import com.shubham.onlinetest.repository.SubmissionRepository;
import com.shubham.onlinetest.service.ActionService;
import com.shubham.onlinetest.service.ProblemService;
import com.shubham.onlinetest.service.UserProblemsService;
import com.shubham.onlinetest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActionServiceImpl implements ActionService {
    private final UserProblemsService userProblemsService;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;
    private final ProblemService problemService;

    public ActionServiceImpl(UserProblemsService userProblemsService, UserService userService, SubmissionRepository submissionRepository, ProblemService problemService) {
        this.userProblemsService = userProblemsService;
        this.userService = userService;
        this.submissionRepository = submissionRepository;
        this.problemService = problemService;
    }

    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username) {
        String errorMessage = "Success";
        User user = userService.getUserByUsername(username);
        Problem problem = problemService.getProblemById(submitRequest.getProblemId());
        UserProblem userProblem = null;

        try {
            userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                    user.getId(),
                    problem.getId()
            );
        }catch (UserProblemNotFoundException e) {
            userProblem = new UserProblem();
            userProblem.setUserId(user.getId());
            userProblem.setProblemId(submitRequest.getProblemId());
            userProblem.setStatus(ProblemStatus.PENDING);
            userProblem = userProblemsService.saveUserProblem(userProblem);
        }

        Submission submission = userProblem.getSubmissions().stream()
                                .filter(s -> (s.getStatus() == SubmissionStatus.COMPILED
                                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED))
                                .findFirst().orElse(new Submission());

        submission.setUserProblem(userProblem);
        submission.setCode(submitRequest.getCode());
        submission.setLanguage(submitRequest.getLanguage());

        //TODO: Compile Code and return output
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        errorMessage = "Compiled";
        submission.setStatus(SubmissionStatus.COMPILED);
        // TODO: Change this block to compile code received from user

        submission = submissionRepository.save(submission);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage,submissionDTO);
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        String errorMessage = "Success";
        UserProblem userProblem = userProblemsService.getUserProblemByID(execRequest.getUserProblemId());
        Optional<Submission> submissionOptional = userProblem.getSubmissions()
                                            .stream()
                                            .filter(s -> s.getStatus() == SubmissionStatus.COMPILED)
                                            .findFirst();

        if(submissionOptional.isEmpty())
            throw new SubmissionNotFoundException("No Compiled Submission Found, Compile the code first");

        Submission submission = submissionOptional.get();

        //TODO: Execute the program and return status
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        errorMessage = "Time Limit Exceeds";
        submission.setStatus(SubmissionStatus.ACCEPTED);
        submission.setRuntimeInMs(20000);
        submission.setMemoryInBytes(30000);
        // TODO: Change this block to execute actual logic of running code

        submission = submissionRepository.save(submission);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage,submissionDTO);
    }
}
