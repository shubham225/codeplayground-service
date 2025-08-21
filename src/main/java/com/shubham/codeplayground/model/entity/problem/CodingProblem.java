package com.shubham.codeplayground.model.entity.problem;

import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CodingProblem extends Problem {
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CodeSnippet> codeSnippets = new ArrayList<>();
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Testcase> testCases = new ArrayList<>();
    private long maxExecutionTime;
}
