package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.Language;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodeSnippetDTO {
    private Language language;
    @NotBlank
    private String codeSnippet;
    private String mainCode;
    private String answerCode;
}
