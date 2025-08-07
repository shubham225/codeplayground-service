package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.*;
import com.shubham.codeplayground.model.entity.CodingProblem;

import java.util.List;
import java.util.UUID;

public interface ProblemService {
    public List<ProblemSummeryDTO> getAllProblemSummery(String username);
    public List<CodingProblem> getAllProblems();
    public ProblemDTO getProblemInfoById(UUID id, String username);
    public CodingProblem getProblemById(UUID id);
    public ProblemDTO createProblem(CreateProblemDTO problemDTO);
    public IdentifierDTO addCodeSnippet(UUID id, CreateCodeSnippetDTO codeInfoDTO);
    public IdentifierDTO addTestCases(UUID id);
}
