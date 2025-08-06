package com.shubham.codeplayground.repository;

import com.shubham.codeplayground.model.entity.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProblemsRepository extends JpaRepository<UserProblem, UUID> {
    Optional<UserProblem> findByUserIdAndProblemId(UUID userId, UUID problemId);
}
