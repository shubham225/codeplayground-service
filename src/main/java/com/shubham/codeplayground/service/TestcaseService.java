package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.entity.Testcase;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

public interface TestcaseService {
    public List<Testcase> parseTestcasesFromFile(UUID Id);
    public List<Testcase> parseTestcasesFromBuffer(String buffer);
}
