package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class ActiveProblem extends BaseModel {
    private UUID userId;
    private UUID problemId;
    @Enumerated(EnumType.ORDINAL)
    private ProblemStatus status;

    @OneToMany(mappedBy = "activeProblem", fetch = FetchType.EAGER)
    private List<Submission> submissions = new ArrayList<>();

    public ActiveProblem() {
        status = ProblemStatus.OPEN;
    }
}
