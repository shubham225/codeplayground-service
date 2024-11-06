package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ActionDTO {
    private UUID submissionId;
    private SubmissionStatus status;
    private String message;
    private SubmissionDTO submission;
}
