package com.shubham.onlinetest.model.mapper;

import com.shubham.onlinetest.model.dto.ProblemSummeryDTO;
import com.shubham.onlinetest.model.entity.Problem;
import com.shubham.onlinetest.model.enums.ProblemStatus;

public class ProblemSummeryMapper {
    public static ProblemSummeryDTO toDto(Problem problem, ProblemStatus status) {
        return ProblemSummeryDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .urlCode(problem.getUrlCode())
                .difficulty(problem.getDifficulty())
                .status(status)
                .summery("Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.")
                .build();
    }
}
