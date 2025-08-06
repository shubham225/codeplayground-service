package com.shubham.codeplayground.service.model;

import com.shubham.codeplayground.model.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunResult {
    private SubmissionStatus status;
    private String message;
    private long runtimeInMs;
    private long memoryInBytes;
}
