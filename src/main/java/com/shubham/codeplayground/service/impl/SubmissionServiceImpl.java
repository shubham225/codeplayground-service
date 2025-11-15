package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.config.properties.AppProperties;
import com.shubham.codeplayground.exception.CodeExecutionException;
import com.shubham.codeplayground.exception.SubmissionNotFoundException;
import com.shubham.codeplayground.exception.UserProblemNotFoundException;
import com.shubham.codeplayground.model.dto.ActionDTO;
import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.model.mapper.SubmissionMapper;
import com.shubham.codeplayground.model.result.CodeRunResult;
import com.shubham.codeplayground.repository.SubmissionRepository;
import com.shubham.codeplayground.service.ActiveProblemsService;
import com.shubham.codeplayground.service.ProblemService;
import com.shubham.codeplayground.service.SubmissionService;
import com.shubham.codeplayground.service.UserService;
import com.shubham.codeplayground.service.execution.runner.CodeRunner;
import com.shubham.codeplayground.service.execution.runner.CodeRunnerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final ActiveProblemsService activeProblemsService;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;
    private final ProblemService problemService;
    private final CodeRunnerFactory codeRunnerFactory;
    private final AppProperties appProperties;

    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username) {
        String errorMessage = "Success";
        User user = userService.getUserByUsername(username);
        CodingProblem problem = problemService.getProblemById(submitRequest.getProblemId());
        ActiveProblem activeProblem = null;
        Language language = submitRequest.getLanguage();

        try {
            activeProblem = activeProblemsService.getActiveProblemByUserAndProblemId(
                    user.getId(),
                    problem.getId()
            );
        } catch (UserProblemNotFoundException e) {
            activeProblem = new ActiveProblem();
            activeProblem.setUserId(user.getId());
            activeProblem.setProblemId(submitRequest.getProblemId());
            activeProblem.setStatus(ProblemStatus.PENDING);
            activeProblem = activeProblemsService.saveActiveProblem(activeProblem);
        }

        Submission submission = activeProblem.getSubmissions().stream()
                .filter(s -> ((s.getStatus() == SubmissionStatus.COMPILED
                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED) && s.getLanguage() == language))
                .findFirst().orElse(new Submission());

        submission.setActiveProblem(activeProblem);
        submission.setCode(submitRequest.getCode());
        submission.setLanguage(submitRequest.getLanguage());

        submission = submissionRepository.save(submission);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage, submissionDTO);
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        String errorMessage = "Success";
        ActiveProblem activeProblem = activeProblemsService.getActiveProblemById(execRequest.getUserProblemId());
        Language language = execRequest.getLanguage();
        Optional<Submission> submissionOptional = activeProblem.getSubmissions()
                .stream()
                .filter(s -> ((s.getStatus() == SubmissionStatus.COMPILED
                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED) && s.getLanguage() == language))
                .findFirst();
        if (submissionOptional.isEmpty())
            throw new SubmissionNotFoundException("No Active Submission Found, Submit the code first");

        Submission submission = submissionOptional.get();

        return executeSubmission(submission.getId());
    }

    @Override
    public ActionDTO executeSubmission(UUID submissionId) {
        String errorMessage = "Success";
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(() -> new SubmissionNotFoundException("No Active Submission Found, Submit the code first"));
        ActiveProblem activeProblem = submission.getActiveProblem();
        CodingProblem problem = problemService.getProblemById(activeProblem.getProblemId());
        User user = userService.getUserById(activeProblem.getUserId());

        Path userDirectory = Paths.get(appProperties.getHomeDir(), user.getUsername());

        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(getCodeRunnerType(submission.getLanguage()));
        try {
            CodeRunResult result = codeRunner.validateSubmission(submission, problem, userDirectory.toString());
            errorMessage = result.getMessage();
            submission.setStatus(result.getStatus());
            submission.setRuntimeInMs(result.getRuntimeInMs());
            submission.setMemoryInBytes(result.getMemoryInBytes());
            submission.setDate(new Date());
        } catch (Exception e) {
            throw new CodeExecutionException(e.getMessage());
        }

        submission = submissionRepository.save(submission);

        if (submission.getStatus() == SubmissionStatus.ACCEPTED)
            activeProblem.setStatus(ProblemStatus.SOLVED);

        activeProblem = activeProblemsService.saveActiveProblem(activeProblem);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage, submissionDTO);
    }

    private String getCodeRunnerType(Language language) {
        switch (language) {
            case JAVA -> {
                return "JAVA";
            }
            case JAVASCRIPT -> {
                return "JAVASCRIPT";
            }
        }

        return "JAVA";
    }
}
