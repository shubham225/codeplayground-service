package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Submission extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_problem_id", nullable = false)
    private UserProblem userProblem;
    private Date date;
    @Enumerated(EnumType.ORDINAL)
    private SubmissionStatus status;
    @Column(columnDefinition = "TEXT")
    private String code;
    @Enumerated(EnumType.ORDINAL)
    private Language language;
    private long runtimeInMs;
    private long memoryInBytes;

    public Submission() {
        date = new Date();
        status = SubmissionStatus.IN_PROGRESS;
    }
}
