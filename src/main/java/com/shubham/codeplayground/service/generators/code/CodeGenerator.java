package com.shubham.codeplayground.service.generators.code;

import com.shubham.codeplayground.model.dto.CodeStubDTO;

public interface CodeGenerator {
    public String generateDriverCode(CodeStubDTO codeStub);
    public String generateCodeStubString(CodeStubDTO codeStub);
}
