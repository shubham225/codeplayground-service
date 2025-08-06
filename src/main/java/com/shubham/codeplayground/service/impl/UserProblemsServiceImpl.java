package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.UserProblemNotFoundException;
import com.shubham.codeplayground.model.dto.CodeDTO;
import com.shubham.codeplayground.model.dto.SubmissionDTO;
import com.shubham.codeplayground.model.entity.Submission;
import com.shubham.codeplayground.model.entity.UserProblem;
import com.shubham.codeplayground.model.mapper.SubmissionMapper;
import com.shubham.codeplayground.repository.UserProblemsRepository;
import com.shubham.codeplayground.service.UserProblemsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserProblemsServiceImpl implements UserProblemsService {
    private final UserProblemsRepository userProblemsRepository;

    public UserProblemsServiceImpl(UserProblemsRepository userProblemsRepository) {
        this.userProblemsRepository = userProblemsRepository;
    }

    @Override
    public UserProblem getUserProblemByUserAndProblemID(UUID userId, UUID problemID) {
        Optional<UserProblem> userProblemOptional = userProblemsRepository.findByUserIdAndProblemId(userId, problemID);

        if(userProblemOptional.isEmpty())
            throw new UserProblemNotFoundException("User Problem Doesn't Exists");

        return userProblemOptional.get();
    }

    @Override
    public UserProblem getUserProblemByID(UUID id) {
        Optional<UserProblem> userProblemOptional = userProblemsRepository.findById(id);

        if(userProblemOptional.isEmpty())
            throw new UserProblemNotFoundException("User Problem Doesn't Exists");

        return userProblemOptional.get();
    }

    @Override
    public Set<CodeDTO> getLatestUserCode(UUID id) {
        Set<Submission> submissions = getSubmissionsByUserProblemId(id);

        Set<Submission> latestSubmissionsByLang = submissions.stream()
                .collect(Collectors.groupingBy(
                        Submission::getLanguage,
                        Collectors.maxBy(Comparator.comparing(Submission::getDate))
                ))
                .values().stream()
                .flatMap(Optional::stream) // Flatten Optional to stream only present values
                .collect(Collectors.toSet());

        return latestSubmissionsByLang.stream()
                .map(s -> new CodeDTO(s.getLanguage(), s.getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Submission> getSubmissionsByUserProblemId(UUID id) {
        return new HashSet<>(getUserProblemByID(id).getSubmissions());
    }

    @Override
    public Set<SubmissionDTO> getSubmissionDTOByUserProblemId(UUID id) {
        return getUserProblemByID(id).getSubmissions().stream()
                .map(SubmissionMapper::toDto)
                .sorted(Comparator.comparing(SubmissionDTO::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public UserProblem saveUserProblem(UserProblem userProblem) {
        return userProblemsRepository.save(userProblem);
    }
}
