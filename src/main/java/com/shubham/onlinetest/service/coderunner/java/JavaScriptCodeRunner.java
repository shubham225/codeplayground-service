package com.shubham.onlinetest.service.coderunner.java;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.service.coderunner.CodeRunner;
import com.shubham.onlinetest.service.model.CodeRunResult;
import com.shubham.onlinetest.service.model.LanguageProperties;

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
