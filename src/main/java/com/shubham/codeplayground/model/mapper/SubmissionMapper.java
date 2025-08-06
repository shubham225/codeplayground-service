package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;

public class SubmissionMapper {
    public static SubmissionDTO toDto(Submission submission) {
        return SubmissionDTO.builder()
                .id(submission.getId())
                .userProblemId(submission.getUserProblem().getId())
                .date(submission.getDate())
                .language(submission.getLanguage())
                .memory(submission.getMemoryInBytes())
                .runtime(submission.getRuntimeInMs())
                .status(submission.getStatus())
                .build();
    }
}
