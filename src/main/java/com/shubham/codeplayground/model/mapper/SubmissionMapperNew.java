package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import org.mapstruct.Mapper;

@Mapper
public interface SubmissionMapperNew {
    SubmissionDTO toDto(Submission submission);
}
