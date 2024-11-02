package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.ProblemDifficulty;
import com.shubham.onlinetest.model.enums.ProblemStatus;
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
    private String urlCode;
    private ProblemStatus status;
    private ProblemDifficulty difficulty;
}
