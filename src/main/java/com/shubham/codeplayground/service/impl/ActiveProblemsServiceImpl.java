package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.ActiveProblemNotFoundException;
import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.ActiveProblem;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.mapper.SubmissionMapper;
import com.shubham.codeplayground.repository.ActiveProblemRepository;
import com.shubham.codeplayground.service.ActiveProblemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActiveProblemsServiceImpl implements ActiveProblemsService {
    private final ActiveProblemRepository activeProblemRepository;
    private final SubmissionMapper submissionMapper;

    @Override
    public ActiveProblem getActiveProblemByUserAndProblemId(UUID userId, UUID problemID) {
        return activeProblemRepository
                .findByUserIdAndProblemId(userId, problemID)
                .orElse(new ActiveProblem());
    }

    @Override
    public ActiveProblem getActiveProblemById(UUID id) {
        return activeProblemRepository
                .findById(id)
                .orElseThrow(() -> new ActiveProblemNotFoundException(
                        MessageFormat.format("Active problem with id {0} don't exist.", id))
                );
    }

    @Override
    public Set<CodeDTO> getLatestUserCodeByActiveProblemId(UUID id) {
        Set<Submission> submissions = getActiveProblemById(id).getSubmissions();

        return submissions.stream()
                .collect(Collectors.groupingBy(
                        Submission::getLanguage,
                        Collectors.maxBy(Comparator.comparing(Submission::getDate))
                ))
                .values().stream()
                .flatMap(Optional::stream)
                .map(submission -> new CodeDTO(submission.getLanguage(), submission.getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SubmissionDTO> getSubmissionsByActiveProblemId(UUID id) {
        // LinkedHashSet is returned so that sorted order is preserved.
        return getActiveProblemById(id).getSubmissions().stream()
                .map(submissionMapper::toDto)
                .sorted(Comparator.comparing(SubmissionDTO::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public ActiveProblem saveActiveProblem(ActiveProblem activeProblem) {
        return activeProblemRepository.save(activeProblem);
    }
}
