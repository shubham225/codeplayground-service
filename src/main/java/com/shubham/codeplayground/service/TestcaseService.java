package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.FileDTO;
import com.shubham.codeplayground.model.entity.Testcase;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TestcaseService {
    public List<Testcase> parseTestcasesFromFile(UUID Id);
    public List<Testcase> parseTestcasesFromFileDtos(Set<FileDTO> files);
    public List<Testcase> parseTestcasesFromBuffer(String buffer);
}
