package com.shubham.codeplayground.repository;

import com.shubham.codeplayground.model.entity.ActiveProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActiveProblemRepository extends JpaRepository<ActiveProblem, UUID> {
    Optional<ActiveProblem> findByUserIdAndProblemId(UUID userId, UUID problemId);
}
