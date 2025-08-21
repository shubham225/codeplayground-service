package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import com.shubham.codeplayground.model.enums.ProblemStatus;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO {
    private UUID id;
    private UUID userProblemId;
    private String title;
    private String descriptionMd;
    private ProblemDifficulty difficulty;
    private ProblemStatus status;
    private List<CodeDTO> codeSnippets;
    private Set<SubmissionDTO> submissions;
}
