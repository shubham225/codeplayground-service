package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.config.properties.AppProperties;
import com.shubham.codeplayground.exception.CodeExecutionException;
import com.shubham.codeplayground.exception.SubmissionNotFoundException;
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
import com.shubham.codeplayground.model.mapper.ActionMapper;
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
import java.text.MessageFormat;
import java.util.Date;
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
    private final SubmissionMapper submissionMapper;
    private final ActionMapper actionMapper;

    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username) {
        User user = userService.getUserByUsername(username);
        CodingProblem problem = problemService.getCodingProblemById(submitRequest.getProblemId());
        Language language = submitRequest.getLanguage();
        ActiveProblem activeProblem = activeProblemsService.getActiveProblemByUserAndProblemId(user.getId(),
                                                                                               problem.getId());

        if (activeProblem.getStatus() == ProblemStatus.OPEN) {
            activeProblem.setUserId(user.getId());
            activeProblem.setProblemId(submitRequest.getProblemId());
            activeProblem.setStatus(ProblemStatus.PENDING);
            activeProblem = activeProblemsService.saveActiveProblem(activeProblem);
        }

        Submission submission = activeProblem.getSubmissions().stream()
                .filter(s -> (
                        (s.getStatus() == SubmissionStatus.COMPILED
                                || s.getStatus() == SubmissionStatus.IN_PROGRESS
                                || s.getStatus() == SubmissionStatus.COMPILATION_FAILED)
                                && s.getLanguage() == language))
                .findFirst().orElse(new Submission());

        submission.setActiveProblem(activeProblem);
        submission.setCode(submitRequest.getCode());
        submission.setLanguage(submitRequest.getLanguage());

        submission = submissionRepository.save(submission);

        return actionMapper.toDto(submission, "Success");
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        ActiveProblem activeProblem = activeProblemsService.getActiveProblemById(execRequest.getUserProblemId());
        Language language = execRequest.getLanguage();
        Submission submission = activeProblem.getSubmissions()
                .stream()
                .filter(s -> ((s.getStatus() == SubmissionStatus.COMPILED
                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED) && s.getLanguage() == language))
                .findFirst()
                .orElseThrow(() -> new SubmissionNotFoundException("No Active Submission Found, Submit the code first"));

        return executeSubmission(submission.getId());
    }

    @Override
    public ActionDTO executeSubmission(UUID submissionId) {
        String message = "Success";
        Submission submission = getSubmissionById(submissionId);
        ActiveProblem activeProblem = submission.getActiveProblem();
        CodingProblem problem = problemService.getCodingProblemById(activeProblem.getProblemId());
        User user = userService.getUserById(activeProblem.getUserId());

        String homeDir = appProperties.getHomeDir();
        if (homeDir == null || homeDir.isBlank()) {
            throw new IllegalStateException("homeDir is not configured in application properties");
        }

        Path userDirectory = Paths.get(homeDir, user.getUsername());

        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(getCodeRunnerType(submission.getLanguage()));
        try {
            CodeRunResult result = codeRunner.validateSubmission(submission, problem, userDirectory.toString());
            message = result.getMessage();
            submission.setStatus(result.getStatus());
            submission.setRuntimeInMs(result.getRuntimeInMs());
            submission.setMemoryInBytes(result.getMemoryInBytes());
            submission.setDate(new Date());
        } catch (Exception e) {
            throw new CodeExecutionException(e.getMessage());
        }

        submission = submissionRepository.save(submission);

        if (submission.getStatus() == SubmissionStatus.ACCEPTED){
            activeProblem.setStatus(ProblemStatus.SOLVED);
            activeProblemsService.saveActiveProblem(activeProblem);
        }

        return actionMapper.toDto(submission, message);
    }

    private Submission getSubmissionById(UUID id) {
        return submissionRepository
                .findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException(
                        MessageFormat.format("Active problem with id {0} not found.", id))
                );
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
