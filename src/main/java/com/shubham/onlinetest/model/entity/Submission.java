package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission extends BaseModel {
    @ManyToOne
    private UserProblem userProblem;
    private Date date;
    private SubmissionStatus status;
    @Column(columnDefinition = "TEXT")
    private String code;
    private Language language;
    private long runtimeInMs;
    private long memoryInBytes;
}
