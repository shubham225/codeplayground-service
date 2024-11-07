package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class UserProblem extends BaseModel {
    private UUID userId;
    private UUID problemId;
    @Enumerated(EnumType.ORDINAL)
    private ProblemStatus status;

    @OneToMany(mappedBy = "userProblem", fetch = FetchType.EAGER)
    private List<Submission> submissions = new ArrayList<>();

    public UserProblem() {
        status = ProblemStatus.OPEN;
    }
}
