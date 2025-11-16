package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.ActiveProblemNotFoundException;
import com.shubham.codeplayground.exception.ProblemNotFoundException;
import com.shubham.codeplayground.model.dto.*;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.User;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import com.shubham.codeplayground.model.mapper.CodeMapperNew;
import com.shubham.codeplayground.model.mapper.ProblemMapper;
import com.shubham.codeplayground.model.mapper.ProblemSummeryMapperNew;
import com.shubham.codeplayground.repository.CodeSnippetRepository;
import com.shubham.codeplayground.repository.CodingProblemRepository;
import com.shubham.codeplayground.service.ActiveProblemsService;
import com.shubham.codeplayground.service.ProblemService;
import com.shubham.codeplayground.service.UserService;
import com.shubham.codeplayground.service.generators.problem.ProblemGenerator;
import com.shubham.codeplayground.service.generators.problem.ProblemGeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final CodingProblemRepository codingProblemRepository;
    private final UserService userService;
    private final ActiveProblemsService activeProblemsService;
    private final CodeSnippetRepository codeSnippetRepository;
    private final ProblemGeneratorFactory problemGeneratorFactory;
    private final ProblemSummeryMapperNew problemSummeryMapper;
    private final ProblemMapper problemMapper;
    private final CodeMapperNew codeMapper;

    @Override
    public List<ProblemSummeryDTO> getAllProblemSummery(String username) {
        List<CodingProblem> problems = getAllCodingProblems();
        User user = userService.getUserByUsername(username);

        return problems.stream()
                .map(problem -> {
                    ActiveProblem activeProblem = getSafeActiveProblemByUserAndProblem(user.getId(), problem.getId());
                    ProblemStatus status = activeProblem.getStatus();
                    return problemSummeryMapper.toDto(problem, status);
                })
                .collect(Collectors.toList());
    }

    private ActiveProblem getSafeActiveProblemByUserAndProblem(UUID userId, UUID problemId) {
        try {
            return activeProblemsService.getActiveProblemByUserAndProblemId(userId, problemId);
        } catch (ActiveProblemNotFoundException e) {
            return new ActiveProblem();
        }
    }

    private List<CodingProblem> getAllCodingProblems() {
        return codingProblemRepository.findAll();
    }

    @Override
    public ProblemDTO getProblemInfoById(UUID id, String username) {
        CodingProblem problem = getCodingProblemById(id);
        User user = userService.getUserByUsername(username);
        ActiveProblem activeProblem = getSafeActiveProblemByUserAndProblem(user.getId(), problem.getId());

        return problemMapper.toDto(problem, activeProblem);
    }

    @Override
    public CodingProblem getCodingProblemById(UUID id) {
        return codingProblemRepository.findById(id)
                .orElseThrow(() -> new ProblemNotFoundException(
                        MessageFormat.format("Problem with id {0} not found", id))
                );
    }

    @Override
    public ProblemDTO createProblem(CreateProblemDTO problemDTO) {
        ProblemGenerator problemGenerator = problemGeneratorFactory.getProblemGenerator(
                                                                    problemDTO.getType().toUpperCase());
        // Generate will also save the record in tables
        Problem problem = problemGenerator.generate(problemDTO);

        return problemMapper.toDto(problem);
    }

    @Override
    public IdentifierDTO addCodeSnippet(UUID id, CreateCodeSnippetDTO codeInfoDTO) {
        CodingProblem problem = getCodingProblemById(id);

        CodeSnippet code = problem.getCodeSnippets().stream()
                .filter(c -> c.getLanguage() == codeInfoDTO.getLanguage())
                .findFirst().orElseGet(() -> {
                    CodeSnippet codeNew = codeMapper.toEntity(codeInfoDTO);
                    codeNew.setProblem(problem);
                    codeNew = codeSnippetRepository.save(codeNew);
                    return codeNew;
                });

        return IdentifierDTO.builder()
                .id(code.getId())
                .build();
    }
}
