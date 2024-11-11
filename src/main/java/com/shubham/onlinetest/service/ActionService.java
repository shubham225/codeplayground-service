package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.dto.ActionDTO;
import com.shubham.onlinetest.model.dto.ExecuteReqDTO;
import com.shubham.onlinetest.model.dto.SubmitReqDTO;

public interface ActionService {
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest, String username);
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest);
    public String testCode();
}
