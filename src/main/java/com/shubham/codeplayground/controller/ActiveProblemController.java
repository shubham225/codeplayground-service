package com.shubham.codeplayground.controller;

import com.shubham.codeplayground.constant.ApplicationConstants;
import com.shubham.codeplayground.model.result.AppResult;
import com.shubham.codeplayground.service.ActiveProblemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.shubham.codeplayground.constant.ApplicationConstants.VERSION;


@RestController
@RequestMapping(value = VERSION + "/activeProblems")
public class ActiveProblemController {
    private final ActiveProblemsService activeProblemsService;

    public ActiveProblemController(ActiveProblemsService activeProblemsService) {
        this.activeProblemsService = activeProblemsService;
    }

    @RequestMapping(
            path = "{id}/code",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getLatestUserCodes(@PathVariable UUID id) {
        return AppResult.success(activeProblemsService.getLatestUserCode(id));
    }

    @RequestMapping(
            path = "{id}/submissions",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getSubmissions(@PathVariable UUID id) {
        return AppResult.success(activeProblemsService.getSubmissionDTOByUserProblemId(id));
    }
}
