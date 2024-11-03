package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class SubmissionDTO {
    private UUID userProblemId;
    private Date date;
    private SubmissionStatus status;
    private Language language;
    private long runtime;
    private long memory;
}
