package com.shubham.onlinetest.model.mapper;

import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.entity.CodeSnippet;
import com.shubham.onlinetest.model.entity.Problem;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.model.enums.ProblemStatus;

import java.util.stream.Collectors;

public class ProblemMapper {
    public static ProblemDTO toDto(Problem problem, String description, UserProblem userProblem) {
        if (problem == null)
            return null;

        return ProblemDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .urlCode(problem.getUrlCode())
                .status(userProblem.getStatus())
                .descriptionMd(description)
                .difficulty(problem.getDifficulty())
                .codeSnippets(problem.getCodeSnippets()
                        .stream()
                        .map(CodeMapper::toDto)
                        .collect(Collectors.toList()))
                .languages(problem.getCodeSnippets()
                        .stream()
                        .map(CodeSnippet::getLanguage)
                        .collect(Collectors.toList()))
                .build();
    }

    public Problem toEntity(ProblemDTO dto) {
        if (dto == null) {
            return null;
        }
        Problem problem = new Problem();
        problem.setId(dto.getId());

        return problem;
    }
}
