package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.exception.ProblemNotFoundException;
import com.shubham.onlinetest.model.dto.CreateProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemSummeryDTO;
import com.shubham.onlinetest.model.entity.Problem;
import com.shubham.onlinetest.model.entity.User;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.ProblemStatus;
import com.shubham.onlinetest.model.mapper.ProblemMapper;
import com.shubham.onlinetest.model.mapper.ProblemSummeryMapper;
import com.shubham.onlinetest.repository.ProblemRepository;
import com.shubham.onlinetest.service.ProblemService;
import com.shubham.onlinetest.service.UserProblemsService;
import com.shubham.onlinetest.service.UserService;
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

    public ProblemServiceImpl(ProblemRepository problemRepository, UserService userService, UserProblemsService userProblemsService) {
        this.problemRepository = problemRepository;
        this.userService = userService;
        this.userProblemsService = userProblemsService;
    }

    @Override
    public List<ProblemSummeryDTO> getAllProblemSummery(String username) {
        List<Problem> problems = getAllProblems();
        User user = userService.getUserByUsername(username);

        return problems.stream().map( problem -> {
           UserProblem userProblem = userProblemsService.getUserProblemByUserAndProblemID(user.getId(), problem.getId());
           ProblemStatus status = (userProblem != null) ? userProblem.getStatus() : ProblemStatus.OPEN;
           return ProblemSummeryMapper.toDto(problem, status);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public ProblemDTO getProblemInfoById(UUID id, String username) {
        Problem problem = getProblemById(id);

        User user = userService.getUserByUsername(username);

        UserProblem userProblem = userProblemsService.getUserProblemByUserAndProblemID(
                user.getId(),
                problem.getId()
        );

        return ProblemMapper.toDto(problem, problem.getDescriptionMdPath(), userProblem);
    }

    @Override
    public Problem getProblemById(UUID id) {
        Optional<Problem> problem = problemRepository.findById(id);

        if(problem.isEmpty())
            throw new ProblemNotFoundException("Problem with ID '" + id + "'Not Found");

        return problem.get();
    }

    @Override
    public ProblemDTO createProblem(CreateProblemDTO problemDTO) {
        Problem problem = ProblemMapper.toEntity(problemDTO);

        problemRepository.save(problem);

        return ProblemMapper.toDto(problem);
    }
}
