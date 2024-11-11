package com.shubham.onlinetest.service.coderunner;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.service.coderunner.java.JavaCodeRunner;
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
            throw new IllegalArgumentException("No such notification type");
        }
        return codeRunner;
    }
}
