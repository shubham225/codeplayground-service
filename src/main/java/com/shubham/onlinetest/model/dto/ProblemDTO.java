package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.enums.ProblemDifficulty;
import com.shubham.onlinetest.model.enums.ProblemStatus;
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
    private String urlCode;
    private String title;
    private String descriptionMd;
    private ProblemDifficulty difficulty;
    private ProblemStatus status;
    private List<CodeDTO> codeSnippets;
    private Set<Submission> submissions;
}
