package com.shubham.onlinetest.model.mapper;

import com.shubham.onlinetest.model.dto.CreateProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.entity.Problem;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.ProblemStatus;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProblemMapper {
    public static ProblemDTO toDto(Problem problem, String description, UserProblem userProblem) {
        if (problem == null)
            return null;

        return ProblemDTO.builder()
                .id(problem.getId())
                .userProblemId(userProblem.getId())
                .title(problem.getTitle())
                .urlCode(problem.getUrlCode())
                .status(userProblem.getStatus())
                .descriptionMd(description)
                .difficulty(problem.getDifficulty())
                .codeSnippets(problem.getCodeSnippets()
                        .stream()
                        .map(CodeMapper::toDto)
                        .collect(Collectors.toList()))
                .submissions(userProblem.getSubmissions())
                .build();
    }

    public static ProblemDTO toDto(Problem problem) {
        if (problem == null)
            return null;

        return ProblemDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .urlCode(problem.getUrlCode())
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

    public static Problem toEntity(CreateProblemDTO dto) {
        if (dto == null) {
            return null;
        }

        return Problem
                .builder()
                .title(dto.getTitle())
                .urlCode(dto.getUrlCode())
                .descriptionMd(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .maxExecutionTime(dto.getMaxExecutionTime())
                .build();
    }
}
