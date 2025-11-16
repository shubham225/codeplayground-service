package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.ActionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {SubmissionMapper.class})
public interface ActionMapper {

    @Mappings({
            @Mapping(target = "submissionId", source = "submission.id"),
            @Mapping(target = "status", source = "submission.status"),
            @Mapping(target = "message", source = "message"),
            @Mapping(target = "submission", source = "submission")
    })
    ActionDTO toDto(Submission submission, String message);
}
