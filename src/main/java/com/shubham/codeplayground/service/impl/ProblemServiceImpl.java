package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.ProblemNotFoundException;
import com.shubham.codeplayground.exception.UserProblemNotFoundException;
import com.shubham.codeplayground.model.dto.*;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import com.shubham.codeplayground.model.mapper.CodeMapper;
import com.shubham.codeplayground.model.mapper.ProblemMapper;
import com.shubham.codeplayground.model.mapper.ProblemSummeryMapper;
import com.shubham.codeplayground.repository.CodeSnippetRepository;
import com.shubham.codeplayground.repository.CodingProblemRepository;
import com.shubham.codeplayground.service.ProblemService;
import com.shubham.codeplayground.service.ActiveProblemsService;
import com.shubham.codeplayground.service.UserService;
import com.shubham.codeplayground.service.generators.problem.ProblemGenerator;
import com.shubham.codeplayground.service.generators.problem.ProblemGeneratorFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {
    private final CodingProblemRepository codingProblemRepository;
    private final UserService userService;
    private final ActiveProblemsService activeProblemsService;
    private final CodeSnippetRepository codeSnippetRepository;
    private final ProblemGeneratorFactory problemGeneratorFactory;

    public ProblemServiceImpl(CodingProblemRepository codingProblemRepository, UserService userService, ActiveProblemsService activeProblemsService, CodeSnippetRepository codeSnippetRepository, ProblemGeneratorFactory problemGeneratorFactory) {
        this.codingProblemRepository = codingProblemRepository;
        this.userService = userService;
        this.activeProblemsService = activeProblemsService;
        this.codeSnippetRepository = codeSnippetRepository;
        this.problemGeneratorFactory = problemGeneratorFactory;
    }

    @Override
    public List<ProblemSummeryDTO> getAllProblemSummery(String username) {
        List<Problem> problems = getAllProblems();
        User user = userService.getUserByUsername(username);

        Problem problem2 = new Problem();
        problem2.setTitle("Three Sum");
        problem2.setDifficulty(ProblemDifficulty.HARD);
        problems.add(problem2);

        return problems.stream().map(problem -> {
            ActiveProblem activeProblem = null;

            try {
                activeProblem = activeProblemsService.getUserProblemByUserAndProblemID(user.getId(), problem.getId());
            } catch (UserProblemNotFoundException e) {
                activeProblem = new ActiveProblem();
            }

            ProblemStatus status = (activeProblem != null) ? activeProblem.getStatus() : ProblemStatus.OPEN;
            return ProblemSummeryMapper.toDto(problem, status);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Problem> getAllProblems() {
        return codingProblemRepository.findAll().stream().map(codingProblem -> (Problem) codingProblem).toList();
    }

    @Override
    public ProblemDTO getProblemInfoById(UUID id, String username) {
        CodingProblem problem = getProblemById(id);

        User user = userService.getUserByUsername(username);
        ActiveProblem activeProblem = null;

        try {
            activeProblem = activeProblemsService.getUserProblemByUserAndProblemID(user.getId(), problem.getId());
        } catch (UserProblemNotFoundException e) {
            activeProblem = new ActiveProblem();
        }

        return ProblemMapper.toDto(problem, problem.getDescriptionMd(), activeProblem);
    }

    @Override
    public CodingProblem getProblemById(UUID id) {
        Optional<CodingProblem> problem = codingProblemRepository.findById(id);

        if (problem.isEmpty()) throw new ProblemNotFoundException("Problem with ID '" + id + "'Not Found");

        return problem.get();
    }

    @Override
    public ProblemDTO createProblem(CreateProblemDTO problemDTO) {
        // TODO: implement logic to validate and generate problem
        ProblemGenerator problemGenerator = problemGeneratorFactory.getProblemGenerator("CODING");

        //Generate will also save the record in tables
        Problem problem = problemGenerator.generate(problemDTO);

        return ProblemMapper.toDto((CodingProblem) problem);
    }

    @Override
    public IdentifierDTO addCodeSnippet(UUID id, CreateCodeSnippetDTO codeInfoDTO) {
        CodingProblem problem = getProblemById(id);

        CodeSnippet code = problem.getCodeSnippets().stream().filter(c -> c.getLanguage() == codeInfoDTO.getLanguage()).findFirst().orElse(null);

        if (code == null) {
            code = CodeMapper.toEntity(codeInfoDTO);
            code.setProblem(problem);
            code = codeSnippetRepository.save(code);
        }

        return IdentifierDTO.builder().id(code.getId()).build();
    }

    @Override
    public IdentifierDTO addTestCases(UUID id) {
        return null;
    }
}
