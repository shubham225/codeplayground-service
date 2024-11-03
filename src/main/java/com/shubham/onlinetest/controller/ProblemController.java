package com.shubham.onlinetest.controller;

import com.shubham.onlinetest.model.dto.CreateProblemDTO;
import com.shubham.onlinetest.model.dto.CreateCodeSnippetDTO;
import com.shubham.onlinetest.model.result.AppResult;
import com.shubham.onlinetest.service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

import static com.shubham.onlinetest.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "/problems")
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {

        this.problemService = problemService;
    }

    @RequestMapping(
            path = "",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getAllProblems(Principal principal) {
        String username = (principal != null) ? principal.getName() : "guest";
        return AppResult.success(problemService.getAllProblemSummery(username));
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<AppResult> getProblemById(@PathVariable UUID id,
                                                    Principal principal) {
        String username = (principal != null) ? principal.getName() : "guest";
        return AppResult.success(problemService.getProblemInfoById(id, username));
    }

    @RequestMapping(
            path = "",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> addNewProblem(@RequestBody CreateProblemDTO createRequest) {
        return AppResult.created(problemService.createProblem(createRequest));
    }

    @RequestMapping(
            path = "/{id}/codeSnippets",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> addCodeSnippet(@PathVariable UUID id,
                                                    @RequestBody CreateCodeSnippetDTO codeInfoDTO) {
        return AppResult.created(problemService.addCodeSnippet(id, codeInfoDTO));
    }

    @RequestMapping(
            path = "/{id}/testcases",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> addTestCases(@PathVariable UUID id) {
        return AppResult.created(problemService.addTestCases(id));
    }
}
