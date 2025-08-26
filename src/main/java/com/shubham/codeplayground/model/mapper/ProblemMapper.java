package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.CreateProblemDTO;
import com.shubham.codeplayground.model.dto.ProblemDTO;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.ProblemStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProblemMapper {
    public static ProblemDTO toDto(Problem problem, String description, ActiveProblem activeProblem) {
        if (problem == null)
            return null;

        return ProblemDTO.builder()
                .id(problem.getId())
                .userProblemId((activeProblem != null) ? activeProblem.getId() : null)
                .title(problem.getTitle())
                .status((activeProblem != null) ? activeProblem.getStatus() : ProblemStatus.OPEN)
                .descriptionMd(description)
                .difficulty(problem.getDifficulty())
                .codeSnippets(problem.getCodeSnippets()
                        .stream()
                        .map(CodeMapper::toDto)
                        .collect(Collectors.toList()))
                .submissions((activeProblem != null) ? activeProblem.getSubmissions().stream()
                        .map(SubmissionMapper::toDto)
                        .collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

    public static ProblemDTO toDto(CodingProblem problem) {
        if (problem == null)
            return null;

        return ProblemDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .status(ProblemStatus.OPEN)
                .descriptionMd(problem.getDescriptionMd())
                .difficulty(problem.getDifficulty())
                .codeSnippets(Optional.ofNullable(problem.getCodeSnippets())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(CodeMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static CodingProblem toEntity(CreateProblemDTO dto) {
        if (dto == null) {
            return null;
        }

//        return CodingProblem
//                .builder()
//                .title(dto.getTitle())
//                .urlCode(dto.getUrlCode())
//                .descriptionMd(dto.getDescription())
//                .difficulty(dto.getDifficulty())
//                .maxExecutionTime(dto.getMaxExecutionTime())
//                .build();

        CodingProblem problem = new CodingProblem();

        problem.setTitle(dto.getTitle());
        problem.setDescriptionMd(dto.getDescription());
        problem.setDifficulty(dto.getDifficulty());
        problem.setMaxExecutionTime(dto.getMaxExecutionTime());

        return problem;
    }
}
