package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.model.helper.LanguageProperties;
import com.shubham.codeplayground.model.result.CodeExecutorResult;
import com.shubham.codeplayground.model.result.CodeRunResult;
import com.shubham.codeplayground.service.ProblemService;
import com.shubham.codeplayground.service.execution.executor.CodeExecutorService;
import com.shubham.codeplayground.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component("JAVA")
@RequiredArgsConstructor
public class JavaCodeRunner implements CodeRunner {
    private final CodeExecutorService codeExecutorService;

    public static String convertParams(String input) {
        // Remove outer square brackets if any
        input = input.trim();
        if (input.startsWith("[")) input = input.substring(1);
        if (input.endsWith("]")) input = input.substring(0, input.length() - 1);

        List<String> result = new ArrayList<>();

        int brace = 0;
        StringBuilder current = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == ',' && brace == 0) {
                addParam(result, current.toString().trim());
                current.setLength(0);
            } else {
                if (c == '{') brace++;
                else if (c == '}') brace--;
                current.append(c);
            }
        }

        if (current.length() > 0)
            addParam(result, current.toString().trim());

        return String.join(" ", result);
    }

    private static void addParam(List<String> result, String param) {
        if (param.startsWith("{") && param.endsWith("}")) {
            // Handle array
            String inside = param.substring(1, param.length() - 1).trim();
            if (!inside.isEmpty()) {
                String[] elements = inside.split("\\s*,\\s*");
                result.add(String.valueOf(elements.length));
                result.addAll(Arrays.asList(elements));
            } else {
                result.add("0"); // empty array
            }
        } else {
            result.add(param);
        }
    }

    @Override
    public CodeRunResult validate(String driverCode, String code, String userHome, String testCasePath, String answerKeyPath) {
        CodeRunResult result = new CodeRunResult();
        result.setStatus(SubmissionStatus.IN_PROGRESS);

        String className = "Driver";
        String solutionClassName = "Solution";
        String testcasesFile = "testcases.txt";
        String resultFile = "result.txt";

        String userTestCase = userHome + "/" + testcasesFile;
        String resultFilePath = userHome + "/" + resultFile;

        FileUtils.createFileWithContents(Paths.get(userHome, (className + ".java")).toString(), driverCode);
        FileUtils.createFileWithContents(Paths.get(userHome, (solutionClassName + ".java")).toString(), code);

        Path testCasesPath = Paths.get(testCasePath);
        try {
            Files.copy(testCasesPath, Paths.get(userTestCase), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CodeExecutorResult output = codeExecutorService.compileCode(className + ".java", "", userHome, getLanguageProperties());

        if (!output.isSuccess()) {
            result.setStatus(SubmissionStatus.COMPILATION_FAILED);
            result.setMessage("Compilation failed: \n\n" + output.getOutput().toString());
            result.setMemoryInBytes(output.getMemoryUsage());
            result.setRuntimeInMs(output.getExecTime());
            deleteDirectory(new File(userHome));

            return result;
        }

        // TODO: execute will return testcase output
        String arguments = "./" + testcasesFile + " " + "./" + resultFile;
        output = codeExecutorService.executeCode(className, arguments, userHome, getLanguageProperties());

        if (!output.isSuccess()) {
            result.setStatus(SubmissionStatus.RUNTIME_ERROR);
            result.setMessage("Runtime Error: \n\n" + output.getOutput().toString());
            result.setMemoryInBytes(output.getMemoryUsage());
            result.setRuntimeInMs(output.getExecTime());
            deleteDirectory(new File(userHome));

            return result;
        }

        // TODO: Compare the Output with AnswerKey and return result
        Path answerKeypath1 = Paths.get(answerKeyPath);

        List<String> answerKey = new ArrayList<>();
        try {
            answerKey = Files.readAllLines(answerKeypath1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path resultFilePath1 = Paths.get(resultFilePath);

        List<String> resultF = new ArrayList<>();
        try {
            resultF = Files.readAllLines(resultFilePath1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> testCases = new ArrayList<>();
        try {
            testCases = Files.readAllLines(testCasesPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String failedTestCase = validateOutput(resultF, answerKey, testCases);

        deleteDirectory(new File(userHome));
        if (!failedTestCase.isBlank()) {
            result.setStatus(SubmissionStatus.WRONG_ANSWER);
            result.setMessage("Test Case failed: \n\n" + failedTestCase);
            result.setMemoryInBytes(output.getMemoryUsage());
            result.setRuntimeInMs(output.getExecTime());


            return result;
        }

        return new CodeRunResult(SubmissionStatus.ACCEPTED, "Success", output.getMemoryUsage(), output.getExecTime());
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

    @Override
    public CodeRunResult validateSubmission(Submission submission, CodingProblem problem, String userHome) {
        List<Testcase> testcases = problem.getTestCases();
        CodeSnippet codeSnippet = problem.getCodeSnippets().stream()
                .filter(snippet -> snippet.getLanguage() == submission.getLanguage())
                .findFirst()
                .orElse(null);

        CodeRunResult result = new CodeRunResult();
        result.setStatus(SubmissionStatus.IN_PROGRESS);

        String className = "Driver";
        String solutionClassName = "Solution";

        assert codeSnippet != null;
        FileUtils.createFileWithContents(Paths.get(userHome, (className + ".java")).toString(), codeSnippet.getDriverCode());
        FileUtils.createFileWithContents(Paths.get(userHome, (solutionClassName + ".java")).toString(), submission.getCode());

        CodeExecutorResult output = codeExecutorService.compileCode(className + ".java", "", userHome, getLanguageProperties());

        if (!output.isSuccess()) {
            result.setStatus(SubmissionStatus.COMPILATION_FAILED);
            result.setMessage("Compilation failed: \n\n" + output.getOutput().toString());
            result.setMemoryInBytes(output.getMemoryUsage());
            result.setRuntimeInMs(output.getExecTime());
            deleteDirectory(new File(userHome));

            return result;
        }

        // TODO: execute will return testcase output
        for (Testcase t : testcases) {
            String arguments = String.join(" ", "1", convertParams(t.getTestcase()));
            output = codeExecutorService.executeCode(className, arguments, userHome, getLanguageProperties());

            if (!output.isSuccess()) {
                result.setStatus(SubmissionStatus.RUNTIME_ERROR);
                result.setMessage("Runtime Error: \n\n" + output.getOutput().toString());
                result.setMemoryInBytes(output.getMemoryUsage());
                result.setRuntimeInMs(output.getExecTime());
                deleteDirectory(new File(userHome));

                return result;
            }

            // Test if testcase failed
            if (! output.getOutput().get(0).equals(t.getAnswer())) {
                result.setStatus(SubmissionStatus.WRONG_ANSWER);
                result.setMessage("Test Case failed: \n\n" + "Your Answer: " + output.getOutput().get(0) + " Correct Answer: " + t.getAnswer());
                result.setMemoryInBytes(output.getMemoryUsage());
                result.setRuntimeInMs(output.getExecTime());
                return result;
            }

        }

        deleteDirectory(new File(userHome));

        return new CodeRunResult(SubmissionStatus.ACCEPTED, "Success", output.getMemoryUsage(), output.getExecTime());
    }

    private String validateOutput(List<String> output, List<String> answerKey, List<String> testcases) {
        if (output.size() != answerKey.size() || output.size() != testcases.size())
            return (!output.isEmpty()) ? output.get(output.size() - 1) : "Wrong Answer";

        for (int i = 0; i < output.size(); i++) {
            if (!output.get(i).equals(answerKey.get(i))) {
                String[] val = testcases.get(i).split("\\|");
                return "Test Case: " + val[0] + " " + val[1] + "\nYour Answer: " + output.get(i) + "\nCorrect Answer: " + answerKey.get(i);
            }
        }
        return "";
    }

    void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                file.delete();
            }
        }
    }
}
