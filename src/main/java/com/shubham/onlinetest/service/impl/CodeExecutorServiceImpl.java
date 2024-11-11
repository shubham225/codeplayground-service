package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.service.CodeExecutorService;
import com.shubham.onlinetest.service.model.CodeExecutorResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutorServiceImpl implements CodeExecutorService {
    @Override
    public CodeExecutorResult executeCode(String sourceFile, String execDirPath, Language language) {
        CodeExecutorResult output = null;
        try {
            output = executeCodeInDocker("/mnt/d/Temporary/CodingAppData", "openjdk:17", "javac Solution.java && java Solution",true);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    private CodeExecutorResult executeCodeInDocker(String execDirPath,
                                                   String compiler,
                                                   String action,
                                                   boolean execFromWsl) throws IOException, InterruptedException {
        String output = "";
        List<String> command = new ArrayList<>();

        if (execFromWsl) command.add("wsl");
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add(execDirPath + ":/app");
        command.add(compiler);
        command.add("/bin/bash");
        command.add("-c");
        command.add("\"cd /app && " + action + "\"");

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        process.waitFor(1, TimeUnit.MINUTES);

        int exitCode = process.exitValue();

        String stdOutput = readInputStream(process.getInputStream());
        String stdError = readInputStream(process.getErrorStream());

        output = (exitCode == 0) ? stdOutput : stdError;

        return new CodeExecutorResult(exitCode, output);
    }

    private String readInputStream(InputStream inStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }
}
