package com.shubham.codeplayground.service.execution.runner;

import com.shubham.codeplayground.exception.CodeSnippetNotFoundException;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.enums.SubmissionStatus;
import com.shubham.codeplayground.model.helper.LanguageProperties;
import com.shubham.codeplayground.model.result.CodeExecutorResult;
import com.shubham.codeplayground.model.result.CodeRunResult;
import com.shubham.codeplayground.service.execution.executor.CodeExecutorService;
import com.shubham.codeplayground.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("JAVA")
@RequiredArgsConstructor
public class JavaCodeRunner implements CodeRunner {
    private final CodeExecutorService codeExecutorService;

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
        String className = "Driver";
        String solutionClassName = "Solution";

        CodeSnippet codeSnippet = problem.getCodeSnippets().stream()
                .filter(snippet -> snippet.getLanguage() == submission.getLanguage())
                .findFirst()
                .orElseThrow(() -> new CodeSnippetNotFoundException(
                        MessageFormat.format("Code snippet for language {0} not found",submission.getLanguage()))
                );

        FileUtils.createFileWithContents(Paths.get(userHome, (className + ".java")).toString(), codeSnippet.getDriverCode());
        FileUtils.createFileWithContents(Paths.get(userHome, (solutionClassName + ".java")).toString(), submission.getCode());

        CodeExecutorResult output = codeExecutorService.compileCode(className + ".java", "",
                                                                              userHome, getLanguageProperties());

        if (!output.isSuccess()) {
            deleteDirectory(new File(userHome));
            return CodeRunResult.builder()
                    .status(SubmissionStatus.COMPILATION_FAILED)
                    .message(MessageFormat.format("Compilation failed: \n\n{0}", output.getOutput().toString()))
                    .memoryInBytes(output.getMemoryUsage())
                    .runtimeInMs(output.getExecTime()).build();
        }

        for (Testcase t : problem.getTestCases()) {
            String arguments = String.join(" ", "1", convertParams(t.getTestcase()));
            output = codeExecutorService.executeCode(className, arguments, userHome, getLanguageProperties());

            if (!output.isSuccess()) {
                deleteDirectory(new File(userHome));
                return CodeRunResult.builder()
                        .status(SubmissionStatus.RUNTIME_ERROR)
                        .message(MessageFormat.format("Runtime Error: \n\n{0}",output.getOutput().toString()))
                        .memoryInBytes(output.getMemoryUsage())
                        .runtimeInMs(output.getExecTime()).build();
            }

            if (! output.getOutput().get(0).equals(t.getAnswer())) {
                deleteDirectory(new File(userHome));
                return CodeRunResult.builder()
                        .status(SubmissionStatus.WRONG_ANSWER)
                        .message(MessageFormat.format(
                                "Test Case failed: \n\nYour Answer: {0} Correct Answer: {1}",
                                output.getOutput().get(0),
                                t.getAnswer()))
                        .memoryInBytes(output.getMemoryUsage())
                        .runtimeInMs(output.getExecTime()).build();
            }
        }

        deleteDirectory(new File(userHome));
        return CodeRunResult.builder()
                .status(SubmissionStatus.ACCEPTED)
                .message("Success")
                .memoryInBytes(output.getMemoryUsage())
                .runtimeInMs(output.getExecTime()).build();
    }

    void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                file.delete();
            }
        }
    }

    private String convertParams(String input) {
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

        if (!current.isEmpty())
            addParam(result, current.toString().trim());

        return String.join(" ", result);
    }

    private void addParam(List<String> result, String param) {
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

}
