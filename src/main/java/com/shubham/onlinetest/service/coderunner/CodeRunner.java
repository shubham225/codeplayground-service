package com.shubham.onlinetest.service.coderunner;

import com.shubham.onlinetest.service.model.CodeRunResult;
import com.shubham.onlinetest.service.model.LanguageProperties;

public interface CodeRunner {
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath);
    public LanguageProperties getLanguageProperties();
}
