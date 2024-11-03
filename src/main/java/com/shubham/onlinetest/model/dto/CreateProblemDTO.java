package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.enums.Language;
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
public class CreateProblemDTO {
    private String title;
    private String urlCode;
    private String description;
    private ProblemDifficulty difficulty;
    private long maxExecutionTime;
}
