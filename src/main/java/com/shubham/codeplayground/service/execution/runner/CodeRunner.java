package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.service.model.CodeRunResult;
import com.shubham.codeplayground.service.model.LanguageProperties;

public interface CodeRunner {
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath);
    public LanguageProperties getLanguageProperties();
}
