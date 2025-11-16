package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mappings({
            @Mapping(target = "userProblemId", source = "submission.activeProblem.id"),
            @Mapping(target = "memory", source = "submission.memoryInBytes"),
            @Mapping(target = "runtime", source = "submission.runtimeInMs"),
    })
    SubmissionDTO toDto(Submission submission);
}
