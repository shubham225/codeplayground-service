package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemDTO {
    @NotBlank
    private String type;
    @NotBlank
    private String title;
    @NotBlank
    private String descriptionMd;
    private ProblemDifficulty difficulty;
    private String[] languages;
    @NotBlank
    private CodeStubDTO codeStub;
    private Set<FileDTO> testcases;
    private Set<FileDTO> solutions;
    private long maxExecutionTime;
}
