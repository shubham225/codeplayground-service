package com.shubham.codeplayground.model.mapper;

import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.CreateCodeSnippetDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CodeMapper {
    public CodeDTO toDto(CodeSnippet code);
    @Mappings({
            @Mapping(target = "solution", source = "code.answerCode"),
            @Mapping(target = "driverCode", source = "code.mainCode"),
            @Mapping(target = "code", source = "code.codeSnippet"),
    })
    public CodeSnippet toEntity(CreateCodeSnippetDTO code);
}
