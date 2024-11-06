package com.shubham.onlinetest.controller;

import com.shubham.onlinetest.model.result.AppResult;
import com.shubham.onlinetest.service.UserProblemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

import static com.shubham.onlinetest.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "/userProblems")
public class UserProblemController {
    private final UserProblemsService userProblemsService;

    public UserProblemController(UserProblemsService userProblemsService) {
        this.userProblemsService = userProblemsService;
    }

    @RequestMapping(
            path = "{id}/code",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getLatestUserCodes(@PathVariable UUID id) {
        return AppResult.success(userProblemsService.getLatestUserCode(id));
    }

    @RequestMapping(
            path = "{id}/submissions",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getSubmissions(@PathVariable UUID id) {
        return AppResult.success(userProblemsService.getSubmissionsByUserProblemId(id));
    }
}
