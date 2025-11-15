package com.shubham.codeplayground.controller;

import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;
import com.shubham.codeplayground.model.result.AppResult;
import com.shubham.codeplayground.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

import static com.shubham.codeplayground.constant.ApplicationConstants.VERSION;

@RestController
@RequestMapping(value = VERSION + "/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @RequestMapping(
            path = "",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> submitUserCode(@RequestBody SubmitReqDTO submitRequest,
                                                    Principal principal) {
        String username = (principal != null) ? principal.getName() : "guest";
        return AppResult.success(submissionService.submitAndCompileUserCode(submitRequest, username));
    }

    @RequestMapping(
            path = "/execute",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> executeUserCode(@RequestBody ExecuteReqDTO executeRequest) {
        return AppResult.success(submissionService.executeUserCode(executeRequest));
    }

    @RequestMapping(
            path = "/{id}/execute",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> executeSubmission(@RequestParam UUID id) {
        return AppResult.success(submissionService.executeSubmission(id));
    }
}
