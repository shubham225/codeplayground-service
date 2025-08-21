package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.model.result.CodeRunResult;
import com.shubham.codeplayground.model.helper.LanguageProperties;
import org.springframework.stereotype.Component;

@Component("Javascript")
public class JavaScriptCodeRunner implements CodeRunner {
    @Override
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath) {
        return new CodeRunResult(SubmissionStatus.COMPILATION_FAILED, "Not Implemented", 23300, 45000);
    }

    @Override
    public LanguageProperties getLanguageProperties() {
        return LanguageProperties.builder()
                .language(Language.JAVASCRIPT)
                .interpretedLang(true)
                .compileCommand("")
                .execCommand("node")
                .dockerImage("node")
                .build();
    }
}
