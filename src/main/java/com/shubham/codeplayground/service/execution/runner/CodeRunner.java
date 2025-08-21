package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.model.result.CodeRunResult;
import com.shubham.codeplayground.model.helper.LanguageProperties;

public interface CodeRunner {
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath);
    public LanguageProperties getLanguageProperties();
}
