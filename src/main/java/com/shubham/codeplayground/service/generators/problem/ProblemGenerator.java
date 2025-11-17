package com.shubham.codeplayground.service.generators.problem;

import com.shubham.codeplayground.model.dto.CreateProblemDTO;
import com.shubham.codeplayground.model.entity.problem.Problem;

public interface ProblemGenerator {
    Problem generate(CreateProblemDTO createProblemDTO);
}
