package com.shubham.codeplayground.model.result;

import com.shubham.codeplayground.model.enums.SubmissionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunResult {
    private SubmissionStatus status;
    private String message;
    private long runtimeInMs;
    private long memoryInBytes;
}
