package com.shubham.codeplayground.repository;

import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<CodingProblem, UUID> {
}
