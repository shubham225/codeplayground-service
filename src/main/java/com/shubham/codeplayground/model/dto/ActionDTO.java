package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {
    private UUID submissionId;
    private SubmissionStatus status;
    private String message;
    private SubmissionDTO submission;
}
