package com.shubham.onlinetest.service.coderunner;

import com.shubham.onlinetest.service.model.CodeRunResult;

public interface CodeRunner {
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath);
}
