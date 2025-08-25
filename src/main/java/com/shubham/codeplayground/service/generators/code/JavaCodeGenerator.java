package com.shubham.codeplayground.service.generators.code;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import org.springframework.stereotype.Component;

@Component("JAVA_CODE_GENERATOR")
public class JavaCodeGenerator implements CodeGenerator{
    @Override
    public String generateDriverCode(CodeStubDTO codeStub) {
        //TODO: Implementation

        return "";
    }

    @Override
    public String generateCodeStubString(CodeStubDTO codeStub) {
        // TODO: Implementation

        return "";
    }
}
