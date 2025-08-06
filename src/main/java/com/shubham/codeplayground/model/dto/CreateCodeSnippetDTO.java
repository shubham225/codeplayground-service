package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodeSnippetDTO {
    private Language language;
    private String codeSnippet;
    private String mainCode;
    private String answerCode;
}
