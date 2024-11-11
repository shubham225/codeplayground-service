package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.config.properties.AppProperties;
import com.shubham.onlinetest.exception.CodeExecutionException;
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
import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.ProblemStatus;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.model.mapper.SubmissionMapper;
import com.shubham.onlinetest.repository.SubmissionRepository;
import com.shubham.onlinetest.service.*;
import com.shubham.onlinetest.service.model.CodeRunResult;
import com.shubham.onlinetest.service.coderunner.CodeRunner;
import com.shubham.onlinetest.service.coderunner.CodeRunnerFactory;
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
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        errorMessage = "Compiled";
//        submission.setStatus(SubmissionStatus.COMPILED);
        // This Function will only Upload the data now
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
                                            .filter(s -> s.getStatus() == SubmissionStatus.COMPILED
                                                    || s.getStatus() == SubmissionStatus.IN_PROGRESS
                                                    || s.getStatus() == SubmissionStatus.COMPILATION_FAILED)
                                            .findFirst();
        if(submissionOptional.isEmpty())
            throw new SubmissionNotFoundException("No Compiled Submission Found, Compile the code first");

        Submission submission = submissionOptional.get();


        Problem problem = problemService.getProblemById(userProblem.getProblemId());
        User user = userService.getUserById(userProblem.getUserId());
        Language language = submission.getLanguage();
        String driverCode = problem.getCodeSnippets().stream().filter(c -> c.getLanguage() == language).findFirst().orElseThrow().getDriverCode();

        Path userDirectory = Paths.get(appProperties.getHomeDir(), user.getUsername());

        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(getCodeRunnerType(submission.getLanguage()));
        try {
            CodeRunResult result = codeRunner.validate(driverCode, submission.getCode(), userDirectory.toString(), problem.getTestCasesPath(), problem.getAnswerKeyPath());
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

        return new ActionDTO(submission.getId(), submission.getStatus(), errorMessage,submissionDTO);
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

    @Override
    public String testCode() {
        return codeExecutorService.executeCode("","", Language.JAVA).getOutput();
    }
}
