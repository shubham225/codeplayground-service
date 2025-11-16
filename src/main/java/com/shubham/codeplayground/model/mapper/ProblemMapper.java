package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.ProblemDTO;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring",
        uses = { CodeMapperNew.class, SubmissionMapper.class },
        imports = { java.util.stream.Collectors.class, ProblemStatus.class })
public interface ProblemMapper {
    @Mappings({
            @Mapping(target = "id", source = "problem.id"),
            @Mapping(target = "title", source = "problem.title"),
            @Mapping(target = "difficulty", source = "problem.difficulty"),
            @Mapping(target = "descriptionMd", source = "problem.descriptionMd"),
            @Mapping(target = "userProblemId", expression =
                    "java(activeProblem != null ? activeProblem.getId() : null)"),
            @Mapping(target = "status", expression =
                    "java(activeProblem != null ? activeProblem.getStatus() : ProblemStatus.OPEN)"),
            @Mapping(target = "codeSnippets", source = "problem.codeSnippets"),

            // Submissions (set, may be null)
            @Mapping(target = "submissions", expression =
                    "java(activeProblem != null ? activeProblem.getSubmissions().stream().map(SubmissionMapper::toDto).collect(Collectors.toSet()) : new java.util.HashSet<>())")
    })
    ProblemDTO toDto(CodingProblem problem, ActiveProblem activeProblem);

    @SubclassMapping(target = ProblemDTO.class, source = CodingProblem.class)
    ProblemDTO toDto(Problem problem);
}
