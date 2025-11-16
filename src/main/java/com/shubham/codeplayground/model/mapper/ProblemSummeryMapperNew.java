package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.ProblemSummeryDTO;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProblemSummeryMapperNew {
    @Mappings({
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "summery", source = "problem.descriptionMd")
    })
    ProblemSummeryDTO toDto(Problem problem, ProblemStatus status);
}
