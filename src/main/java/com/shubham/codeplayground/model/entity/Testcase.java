package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Testcase extends BaseModel {
    private String testcase;
    private String answer;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private CodingProblem problem;
}
