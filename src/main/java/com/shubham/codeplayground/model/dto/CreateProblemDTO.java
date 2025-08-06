package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemDTO {
    private String title;
    private String urlCode;
    private String description;
    private ProblemDifficulty difficulty;
    private long maxExecutionTime;
}
