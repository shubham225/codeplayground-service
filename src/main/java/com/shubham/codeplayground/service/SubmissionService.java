package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.ActionDTO;
import com.shubham.codeplayground.model.dto.ExecuteReqDTO;
import com.shubham.codeplayground.model.dto.SubmitReqDTO;

import java.util.UUID;

public interface SubmissionService {
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username);
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest);
    public ActionDTO executeSubmission(UUID submissionId);
}
