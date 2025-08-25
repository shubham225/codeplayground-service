package com.shubham.codeplayground.service.generators.problem;

import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.service.execution.runner.CodeRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProblemGeneratorFactory {
    private final Map<String, ProblemGenerator> problemGeneratorMap;

    public ProblemGeneratorFactory(Map<String, ProblemGenerator> problemGeneratorMap) {
        this.problemGeneratorMap = problemGeneratorMap;
    }

    public ProblemGenerator getProblemGenerator(String type) {
        ProblemGenerator problemGenerator = problemGeneratorMap.get(type);

        if (problemGenerator == null) {
            throw new IllegalArgumentException("No such problem type found");
        }

        return problemGenerator;
    }
}
