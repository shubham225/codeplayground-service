package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.dto.CreateProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemSummeryDTO;
import com.shubham.onlinetest.model.entity.Problem;

import java.util.List;
import java.util.UUID;

public interface ProblemService {
    public List<ProblemSummeryDTO> getAllProblemSummery(String username);
    public List<Problem> getAllProblems();
    public ProblemDTO getProblemInfoById(UUID id, String username);
    public Problem getProblemById(UUID id);
    public ProblemDTO createProblem(CreateProblemDTO problemDTO);
}
