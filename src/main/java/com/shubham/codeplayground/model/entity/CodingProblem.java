package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CodingProblem extends Problem {
    private String testCasesPath;
    private String answerKeyPath;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CodeSnippet> codeSnippets = new ArrayList<>();
    private long maxExecutionTime;
}
