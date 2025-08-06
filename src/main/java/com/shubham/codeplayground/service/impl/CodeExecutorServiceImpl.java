package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.service.CodeExecutorService;
import com.shubham.codeplayground.service.model.CodeExecutorResult;
import com.shubham.codeplayground.service.model.LanguageProperties;
import com.shubham.codeplayground.utils.PathUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutorServiceImpl implements CodeExecutorService {
    @Override
    public CodeExecutorResult executeCode(String objectFile, String arguments, String execDirPath, LanguageProperties language) {
        CodeExecutorResult output = null;
        String action = language.getExecCommand() + " " + objectFile + " " + arguments;

        try {
            output = executeCodeInDocker(execDirPath, language.getDockerImage(), action,false);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    @Override
    public CodeExecutorResult compileCode(String sourceFile, String arguments, String execDirPath, LanguageProperties language) {
        CodeExecutorResult output = null;
        String action = language.getCompileCommand() + " " + sourceFile + " " + arguments;

        try {
            output = executeCodeInDocker(execDirPath, language.getDockerImage(), action,false);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    private CodeExecutorResult executeCodeInDocker(String execDirPath,
                                                   String compiler,
                                                   String action,
                                                   boolean execFromWsl) throws IOException, InterruptedException {
        List<String> output;
        List<String> command = new ArrayList<>();

        if (execFromWsl) {
            command.add("wsl");
            execDirPath = PathUtils.getWslPath(execDirPath);
        }
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add(execDirPath + ":/app");
        command.add(compiler);
        command.add("/bin/bash");
        command.add("-c");
        command.add("cd /app && " + action);

        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        long pid = process.pid();

        Process psProcess = new ProcessBuilder("ps", "-p", String.valueOf(pid), "-o", "rss=").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));
        String memoryUsage = reader.readLine().trim();

        process.waitFor(1, TimeUnit.MINUTES);

        int exitCode = process.exitValue();

        List<String> stdOutput = readInputStream(process.getInputStream());
        List<String> stdError = readInputStream(process.getErrorStream());

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        output = (exitCode == 0) ? stdOutput : stdError;

        return new CodeExecutorResult(exitCode, output, Integer.parseInt(memoryUsage), elapsedTime);
    }

    private List<String> readInputStream(InputStream inStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        List<String> output = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            output.add(line);
        }

        return output;
    }
}
