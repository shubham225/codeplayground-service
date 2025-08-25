package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.entity.Testcase;

import java.util.List;

public interface TestcaseService {
    public List<Testcase> parseTestcasesFromBuffer(String buffer);
}
