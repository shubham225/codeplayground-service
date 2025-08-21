package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.ProblemSummeryDTO;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.enums.ProblemStatus;

public class ProblemSummeryMapper {
    public static ProblemSummeryDTO toDto(CodingProblem problem, ProblemStatus status) {
        return ProblemSummeryDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .status(status)
                .summery("Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.")
                .build();
    }
}
