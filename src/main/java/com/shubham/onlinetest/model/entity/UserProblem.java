package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.ProblemStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProblem extends BaseModel {
    private UUID userID;
    private UUID problemID;
    private ProblemStatus status;

    @OneToMany
    private Set<Submission> submissions;

}
