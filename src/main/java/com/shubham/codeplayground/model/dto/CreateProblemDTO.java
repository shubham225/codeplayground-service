package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemDTO {
    private String type;
    private String summery;
    private ProblemDifficulty difficulty;
    private String descriptionMd;
    private String[] languages;
    private CodeStubDTO codeStub;
    private long maxExecutionTime;
}
