package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.model.enums.Language;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CodeRunnerFactory {
    private final Map<String, CodeRunner> codeRunnerMap;

    public CodeRunnerFactory(Map<String, CodeRunner> codeRunnerMap) {
        this.codeRunnerMap = codeRunnerMap;
    }

    public CodeRunner getCodeRunner(String language) {
        CodeRunner codeRunner = codeRunnerMap.get(language);
        if (codeRunner == null) {
            throw new IllegalArgumentException("No such language found");
        }
        return codeRunner;
    }
}
