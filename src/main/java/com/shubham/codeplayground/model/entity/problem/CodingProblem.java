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

    public void addCodeSnippet(CodeSnippet snippet) {
        codeSnippets.add(snippet);
        snippet.setProblem(this);
    }

    public void addAllCodeSnippets(List<CodeSnippet> snippets) {
        if (snippets == null || snippets.isEmpty()) return;

        snippets.stream()
                .peek(snippet -> snippet.setProblem(this))
                .forEach(codeSnippets::add);
    }

    public void removeCodeSnippet(CodeSnippet snippet) {
        codeSnippets.remove(snippet);
        snippet.setProblem(null);
    }

    public void addTestcase(Testcase tc) {
        testCases.add(tc);
        tc.setProblem(this);
    }

    public void addAllTestcases(List<Testcase> testcases) {
        if (testcases == null || testcases.isEmpty()) return;

        testcases.stream()
                .peek(testcase -> testcase.setProblem(this))
                .forEach(testCases::add);
    }

    public void removeTestcase(Testcase tc) {
        testCases.remove(tc);
        tc.setProblem(null);
    }
}
