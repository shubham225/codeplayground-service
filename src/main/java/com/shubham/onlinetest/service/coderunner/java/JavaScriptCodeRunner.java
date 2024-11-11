package com.shubham.onlinetest.service.coderunner.java;

import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.service.coderunner.CodeRunner;
import com.shubham.onlinetest.service.model.CodeRunResult;

public class JavaScriptCodeRunner implements CodeRunner {
    @Override
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath) {
        return new CodeRunResult(SubmissionStatus.COMPILATION_FAILED, "Not Implemented", 23300, 45000);
    }
}
