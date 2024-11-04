package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.Language;
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
