package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemDTO {
    private String type;
    private String title;
    private String descriptionMd;
    private ProblemDifficulty difficulty;
    private String[] languages;
    private CodeStubDTO codeStub;
    private Set<FileDTO> testcases;
    private Set<FileDTO> solutions;
    private long maxExecutionTime;
}
