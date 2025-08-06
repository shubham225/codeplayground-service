package com.shubham.codeplayground.service.coderunner.java;

import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.service.coderunner.CodeRunner;
import com.shubham.codeplayground.service.model.CodeRunResult;
import com.shubham.codeplayground.service.model.LanguageProperties;
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
