package com.shubham.onlinetest.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SubmissionDTO {
    private UUID id;
    private UUID userProblemId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy")
    private Date date;
    private SubmissionStatus status;
    private Language language;
    private long runtime;
    private long memory;
}
