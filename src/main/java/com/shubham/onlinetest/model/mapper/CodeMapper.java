package com.shubham.onlinetest.model.mapper;

import com.shubham.onlinetest.model.dto.CodeDTO;
import com.shubham.onlinetest.model.dto.CreateCodeSnippetDTO;
import com.shubham.onlinetest.model.entity.CodeSnippet;

public class CodeMapper {
    public static CodeDTO toDto(CodeSnippet code) {
        return new CodeDTO(code.getLanguage(), code.getCode());
    }

    public static CodeSnippet toEntity(CreateCodeSnippetDTO codeDto) {
        return CodeSnippet.builder()
                .answerCode(codeDto.getAnswerCode())
                .driverCode(codeDto.getMainCode())
                .code(codeDto.getCodeSnippet())
                .language(codeDto.getLanguage())
                .build();
    }
}
