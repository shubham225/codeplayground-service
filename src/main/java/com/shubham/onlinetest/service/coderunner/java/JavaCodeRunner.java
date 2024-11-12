package com.shubham.onlinetest.service.coderunner.java;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.model.enums.SubmissionStatus;
import com.shubham.onlinetest.service.CodeExecutorService;
import com.shubham.onlinetest.service.model.CodeExecutorResult;
import com.shubham.onlinetest.service.model.CodeRunResult;
import com.shubham.onlinetest.service.coderunner.CodeRunner;
import com.shubham.onlinetest.service.model.LanguageProperties;
import com.shubham.onlinetest.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component("Java")
public class JavaCodeRunner implements CodeRunner {
    private final CodeExecutorService codeExecutorService;

    public JavaCodeRunner(CodeExecutorService codeExecutorService) {
        this.codeExecutorService = codeExecutorService;
    }

    @Override
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath) {
        CodeRunResult result = new CodeRunResult();
        result.setStatus(SubmissionStatus.IN_PROGRESS);

        // TODO: Create java files for driver code and user code
        String className = "Driver";
        String solutionClassName = "Solution";

        FileUtils.createFileWithContents(userHome + className + ".java", driverCode);
        FileUtils.createFileWithContents(Paths.get(userHome, (solutionClassName + ".java")).toString(), code);

        // TODO: then compile and execute
        CodeExecutorResult output = codeExecutorService.compileCode(className + ".java",userHome, getLanguageProperties());

        if (!output.isSuccess()) {
            result.setStatus(SubmissionStatus.COMPILATION_FAILED);
            result.setMessage("Compilation failed: " + output.getOutput());
            result.setMemoryInBytes(99999);
            result.setRuntimeInMs(99999);

            return result;
        }

        // TODO: execute will return testcase output
        output = codeExecutorService.executeCode(className,userHome, getLanguageProperties());

        // TODO: Compare the Output with AnswerKey and return result
        return new CodeRunResult(SubmissionStatus.COMPILATION_FAILED, output.getOutput(), 99999, 99999);
    }

    @Override
    public LanguageProperties getLanguageProperties() {
        return LanguageProperties.builder()
                .language(Language.JAVA)
                .interpretedLang(false)
                .compileCommand("javac")
                .execCommand("java")
                .dockerImage("openjdk:17")
                .build();
    }
}
