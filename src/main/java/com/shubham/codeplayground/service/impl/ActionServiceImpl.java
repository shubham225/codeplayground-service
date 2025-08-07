package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.config.properties.AppProperties;
import com.shubham.codeplayground.exception.CodeExecutionException;
import com.shubham.codeplayground.exception.SubmissionNotFoundException;
import com.shubham.codeplayground.exception.UserProblemNotFoundException;
import com.shubham.codeplayground.model.dto.ActionDTO;
import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;
import com.shubham.codeplayground.model.entity.*;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.model.mapper.SubmissionMapper;
import com.shubham.codeplayground.repository.SubmissionRepository;
import com.shubham.codeplayground.service.*;
import com.shubham.codeplayground.service.model.CodeRunResult;
import com.shubham.codeplayground.service.coderunner.CodeRunner;
import com.shubham.codeplayground.service.coderunner.CodeRunnerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service
public class ActionServiceImpl implements ActionService {
    private final UserProblemsService userProblemsService;
    private final UserService userService;
    private final SubmissionRepository submissionRepository;
    private final ProblemService problemService;
    private final CodeExecutorService codeExecutorService;
    private final CodeRunnerFactory codeRunnerFactory;
    private final AppProperties appProperties;

    public ActionServiceImpl(UserProblemsService userProblemsService,
                             UserService userService,
                             SubmissionRepository submissionRepository,
                             ProblemService problemService,
                             CodeExecutorService codeExecutorService,
                             CodeRunnerFactory codeRunnerFactory, AppProperties appProperties) {
        this.userProblemsService = userProblemsService;
        this.userService = userService;
        this.submissionRepository = submissionRepository;
        this.problemService = problemService;
        this.codeExecutorService = codeExecutorService;
        this.codeRunnerFactory = codeRunnerFactory;
        this.appProperties = appProperties;
    }

    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username) {
        String errorMessage = "Success";
        User user = userService.getUserByUsername(username);
        CodingProblem problem = problemService.getProblemById(submitRequest.getProblemId());
        UserProblem userProblem = null;
        Language language = submitRequest.getLanguage();

        try {
            userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                    user.getId(),
                    problem.getId()
            );
        } catch (UserProblemNotFoundException e) {
            userProblem = new UserProblem();
            userProblem.setUserId(user.getId());
            userProblem.setProblemId(submitRequest.getProblemId());
            userProblem.setStatus(ProblemStatus.PENDING);
            userProblem = userProblemsService.saveUserProblem(userProblem);
        }

        Submission submission = userProblem.getSubmissions().stream()
                .filter(s -> ((s.getStatus() == SubmissionStatus.COMPILED
                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED) && s.getLanguage() == language))
                .findFirst().orElse(new Submission());

        submission.setUserProblem(userProblem);
        submission.setCode(submitRequest.getCode());
        submission.setLanguage(submitRequest.getLanguage());

        submission = submissionRepository.save(submission);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage, submissionDTO);
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        String errorMessage = "Success";
        UserProblem userProblem = userProblemsService.getUserProblemByID(execRequest.getUserProblemId());
        Language language = execRequest.getLanguage();
        Optional<Submission> submissionOptional = userProblem.getSubmissions()
                .stream()
                .filter(s -> ((s.getStatus() == SubmissionStatus.COMPILED
                        || s.getStatus() == SubmissionStatus.IN_PROGRESS
                        || s.getStatus() == SubmissionStatus.COMPILATION_FAILED) && s.getLanguage() == language))
                .findFirst();
        if (submissionOptional.isEmpty())
            throw new SubmissionNotFoundException("No Active Submission Found, Submit the code first");

        Submission submission = submissionOptional.get();


        CodingProblem problem = problemService.getProblemById(userProblem.getProblemId());
        User user = userService.getUserById(userProblem.getUserId());

        String driverCode = problem.getCodeSnippets().stream().filter(c -> c.getLanguage() == language).findFirst().orElseThrow().getDriverCode();

        Path userDirectory = Paths.get(appProperties.getHomeDir(), user.getUsername());
        Path testCasesPath = Paths.get(appProperties.getHomeDir(), problem.getTestCasesPath());
        Path answerKeyPath = Paths.get(appProperties.getHomeDir(), problem.getAnswerKeyPath());

        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(getCodeRunnerType(submission.getLanguage()));
        try {
            CodeRunResult result = codeRunner.validate(driverCode, submission.getCode(), userDirectory.toString(), testCasesPath.toString(), answerKeyPath.toString());
            errorMessage = result.getMessage();
            submission.setStatus(result.getStatus());
            submission.setRuntimeInMs(result.getRuntimeInMs());
            submission.setMemoryInBytes(result.getMemoryInBytes());
            submission.setDate(new Date());
        } catch (Exception e) {
            throw new CodeExecutionException(e.getMessage());
        }

        submission = submissionRepository.save(submission);

        SubmissionDTO submissionDTO = SubmissionMapper.toDto(submission);

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage, submissionDTO);
    }

    private String getCodeRunnerType(Language language) {
        switch (language) {
            case JAVA -> {
                return "Java";
            }
            case JAVASCRIPT -> {
                return "Javascript";
            }
        }

        return "Java";
    }
}
