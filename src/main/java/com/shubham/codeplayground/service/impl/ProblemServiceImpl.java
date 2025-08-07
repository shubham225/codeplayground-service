package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.ProblemNotFoundException;
import com.shubham.codeplayground.exception.UserProblemNotFoundException;
import com.shubham.codeplayground.model.dto.*;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.CodingProblem;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.model.entity.UserProblem;
import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import com.shubham.codeplayground.model.mapper.CodeMapper;
import com.shubham.codeplayground.model.mapper.ProblemMapper;
import com.shubham.codeplayground.model.mapper.ProblemSummeryMapper;
import com.shubham.codeplayground.repository.CodeSnippetRepository;
import com.shubham.codeplayground.repository.ProblemRepository;
import com.shubham.codeplayground.service.ProblemService;
import com.shubham.codeplayground.service.UserProblemsService;
import com.shubham.codeplayground.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final UserService userService;
    private final UserProblemsService userProblemsService;
    private final CodeSnippetRepository codeSnippetRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository, UserService userService, UserProblemsService userProblemsService, CodeSnippetRepository codeSnippetRepository) {
        this.problemRepository = problemRepository;
        this.userService = userService;
        this.userProblemsService = userProblemsService;
        this.codeSnippetRepository = codeSnippetRepository;
    }

    @Override
    public List<ProblemSummeryDTO> getAllProblemSummery(String username) {
        List<CodingProblem> problems = getAllProblems();
        User user = userService.getUserByUsername(username);

//        problems.add(CodingProblem.builder().title("Three Sum").difficulty(ProblemDifficulty.HARD).urlCode("TST").build());

        CodingProblem problem2 = new CodingProblem();
        problem2.setTitle("Three Sum");
        problem2.setDifficulty(ProblemDifficulty.HARD);
        problem2.setUrlCode("TST");
        problems.add(problem2);

        return problems.stream().map(problem -> {
            UserProblem userProblem = null;

            try {
                userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                        user.getId(),
                        problem.getId()
                );
            } catch (UserProblemNotFoundException e) {
                userProblem = new UserProblem();
            }

            ProblemStatus status = (userProblem != null) ? userProblem.getStatus() : ProblemStatus.OPEN;
            return ProblemSummeryMapper.toDto(problem, status);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CodingProblem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public ProblemDTO getProblemInfoById(UUID id, String username) {
        CodingProblem problem = getProblemById(id);

        User user = userService.getUserByUsername(username);
        UserProblem userProblem = null;

        try {
            userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                    user.getId(),
                    problem.getId()
            );
        } catch (UserProblemNotFoundException e) {
            userProblem = new UserProblem();
        }

        return ProblemMapper.toDto(problem, problem.getDescriptionMd(), userProblem);
    }

    @Override
    public CodingProblem getProblemById(UUID id) {
        Optional<CodingProblem> problem = problemRepository.findById(id);

        if (problem.isEmpty())
            throw new ProblemNotFoundException("Problem with ID '" + id + "'Not Found");

        return problem.get();
    }

    @Override
    public ProblemDTO createProblem(CreateProblemDTO problemDTO) {
        // TODO: implement logic to validate and generate problem
        CodingProblem problem = ProblemMapper.toEntity(problemDTO);
        problemRepository.save(problem);

        return ProblemMapper.toDto(problem);
    }

    @Override
    public IdentifierDTO addCodeSnippet(UUID id, CreateCodeSnippetDTO codeInfoDTO) {
        CodingProblem problem = getProblemById(id);

        CodeSnippet code = problem.getCodeSnippets()
                .stream()
                .filter(c -> c.getLanguage() == codeInfoDTO.getLanguage())
                .findFirst()
                .orElse(null);

        if (code == null) {
            code = CodeMapper.toEntity(codeInfoDTO);
            code.setProblem(problem);
            code = codeSnippetRepository.save(code);
        }

        return IdentifierDTO.builder()
                .id(code.getId())
                .build();
    }

    @Override
    public IdentifierDTO addTestCases(UUID id) {
        return null;
    }
}
