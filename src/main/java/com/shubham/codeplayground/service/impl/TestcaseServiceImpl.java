package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.InvalidTestcaseFormatException;
import com.shubham.codeplayground.exception.TestCaseNotFoundException;
import com.shubham.codeplayground.model.dto.FileDTO;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.service.StorageService;
import com.shubham.codeplayground.service.TestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestcaseServiceImpl implements TestcaseService {
    private final StorageService storageService;

    @Override
    public List<Testcase> parseTestcasesFromFile(UUID id) {
        return parseTestcasesFromBuffer(storageService.getDatabaseFileContentsAsString(id));
    }

    @Override
    public List<Testcase> parseTestcasesFromFileDtos(Set<FileDTO> files) {
        if (files.isEmpty())
            throw new TestCaseNotFoundException("Testcases not found in request");

        return files.stream()
                .flatMap(file -> parseTestcasesFromFile(file.getId()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Testcase> parseTestcasesFromBuffer(String buffer) {
        if (buffer == null || buffer.isBlank()) {
            return List.of(); // or throw new InvalidTestcaseFormatException("Buffer is empty");
        }

        List<Testcase> testcases = new ArrayList<>();
        String[] lines = buffer.split("\\n");

        for (String line : lines) {
            try {
                testcases.add(parseLine(line));
            } catch (Exception e) {
                throw new InvalidTestcaseFormatException("Invalid testcase format: " + line, e);
            }
        }
        return testcases;
    }

    private static Testcase parseLine(String line) {
        // Format of TestCase File is "[param1, param2] -> output" ex. ["hello","world"] -> "helloworld"
        String[] parts = line.split("\\s*->\\s*");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format: " + line);
        }

        String input = parts[0].trim();
        String output = parts[1].trim();
        input = input.replaceAll("^[|]$", "");
        output = output.replaceAll("^[|]$", "");

        Testcase testcase = new Testcase();

        testcase.setTestcase(input);
        testcase.setAnswer(output);
        testcase.setIsActive(false);

        return testcase;
    }
}
