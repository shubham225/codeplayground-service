package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemSummeryDTO;
import com.shubham.onlinetest.model.entity.Problem;

import java.util.List;
import java.util.UUID;

public interface ProblemService {
    public List<ProblemSummeryDTO> getAllProblemSummery();
    public List<Problem> getAllProblems();
    public ProblemDTO getProblemInfoById(UUID id);
    public Problem getProblemById(UUID id);
}
