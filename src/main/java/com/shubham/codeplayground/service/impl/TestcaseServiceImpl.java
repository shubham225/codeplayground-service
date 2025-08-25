package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.InvalidTestcaseFormatException;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.service.TestcaseService;

import java.util.ArrayList;
import java.util.List;

public class TestcaseServiceImpl implements TestcaseService {
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

    private Testcase parseLine(String line) {
        // TODO: logic to parse single line into Testcase
        return null;
    }
}
