package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.ProblemStatus;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class UserProblem extends BaseModel {
    private UUID userId;
    private UUID problemId;
    private ProblemStatus status;

    @OneToMany
    private Set<Submission> submissions;

    public UserProblem() {
        status = ProblemStatus.OPEN;
        submissions = new HashSet<>();
    }
}
