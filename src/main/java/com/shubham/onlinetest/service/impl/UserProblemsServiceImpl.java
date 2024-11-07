package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.exception.UserProblemNotFoundException;
import com.shubham.onlinetest.model.dto.CodeDTO;
import com.shubham.onlinetest.model.entity.Submission;
import com.shubham.onlinetest.model.entity.UserProblem;
import com.shubham.onlinetest.repository.UserProblemsRepository;
import com.shubham.onlinetest.service.UserProblemsService;
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
    public UserProblem saveUserProblem(UserProblem userProblem) {
        return userProblemsRepository.save(userProblem);
    }
}
