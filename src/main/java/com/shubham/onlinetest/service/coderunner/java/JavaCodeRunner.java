package com.shubham.onlinetest.service.coderunner.java;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.service.CodeExecutorService;
import com.shubham.onlinetest.service.model.CodeExecutorResult;
import com.shubham.onlinetest.service.model.CodeRunResult;
import com.shubham.onlinetest.service.coderunner.CodeRunner;
import org.springframework.stereotype.Component;

@Component("Java")
public class JavaCodeRunner implements CodeRunner {
    private final CodeExecutorService codeExecutorService;

    public JavaCodeRunner(CodeExecutorService codeExecutorService) {
        this.codeExecutorService = codeExecutorService;
    }

    @Override
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath) {
        // TODO: Create java files for driver code and user code
        // TODO: then compile and execute
        // TODO: execute will return testcase output
        // TODO: Compare the Output with AnswerKey and return result
//        CodeExecutorResult output = codeExecutorService.compileCode("","", Language.JAVA);
        CodeExecutorResult output = codeExecutorService.executeCode("","", Language.JAVA);
        return new CodeRunResult(SubmissionStatus.COMPILATION_FAILED, output.getOutput(), 23300, 45000);
    }
}
