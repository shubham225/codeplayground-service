package com.shubham.codeplayground.controller;

import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;
import com.shubham.codeplayground.model.result.AppResult;
import com.shubham.codeplayground.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.shubham.codeplayground.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "/actions")
public class ActionController {
    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @RequestMapping(
            path = "/submit",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> submitUserCode(@RequestBody SubmitReqDTO submitRequest,
                                                    Principal principal) {
        String username = (principal != null) ? principal.getName() : "guest";
        return AppResult.success(actionService.submitAndCompileUserCode(submitRequest, username));
    }

    @RequestMapping(
            path = "/execute",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> executeUserCode(@RequestBody ExecuteReqDTO executeRequest) {
        return AppResult.success(actionService.executeUserCode(executeRequest));
    }
}
