package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.model.dto.ActionDTO;
import com.shubham.onlinetest.model.dto.ExecuteReqDTO;
import com.shubham.onlinetest.model.dto.SubmitReqDTO;
import com.shubham.onlinetest.service.ActionService;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    @Override
    public ActionDTO submitAndCompileUserCode(SubmitReqDTO submitRequest) {
        //TODO: Implementation
        return null;
    }

    @Override
    public ActionDTO executeUserCode(ExecuteReqDTO execRequest) {
        //TODO: Implementation
        return null;
    }
}
