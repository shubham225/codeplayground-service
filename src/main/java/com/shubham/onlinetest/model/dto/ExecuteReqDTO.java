package com.shubham.onlinetest.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExecuteReqDTO {
    private UUID userProblemId;
    private UUID submissionId;
}
