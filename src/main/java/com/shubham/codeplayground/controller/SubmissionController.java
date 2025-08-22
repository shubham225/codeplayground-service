package com.shubham.codeplayground.controller;

import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;
import com.shubham.codeplayground.model.result.AppResult;
import com.shubham.codeplayground.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.shubham.codeplayground.controller.RestApi.VERSION;

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

    // TODO: Change path to /{id}/execute and define the function to process the request
    @RequestMapping(
            path = "/execute",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> executeUserCode(@RequestBody ExecuteReqDTO executeRequest) {
        return AppResult.success(submissionService.executeUserCode(executeRequest));
    }
}
