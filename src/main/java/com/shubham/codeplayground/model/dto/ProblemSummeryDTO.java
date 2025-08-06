package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSummeryDTO {
    private UUID id;
    private String title;
    private String summery;
    private String urlCode;
    private ProblemStatus status;
    private ProblemDifficulty difficulty;
}
