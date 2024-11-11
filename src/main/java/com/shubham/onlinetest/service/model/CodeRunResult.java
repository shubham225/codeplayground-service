package com.shubham.onlinetest.service.model;

import com.shubham.onlinetest.model.enums.SubmissionStatus;
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
