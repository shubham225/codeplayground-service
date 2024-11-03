package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ActionDTO {
    private UUID submissionId;
    private SubmissionStatus status;
    private String message;
    private SubmissionDTO submission;
}
