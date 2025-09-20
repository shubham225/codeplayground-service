package com.shubham.codeplayground.service.generators.code;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeGeneratorTest {
    @Autowired
    private CodeGenerator codeGenerator;
    private CodeStubDTO codeStub;

    @BeforeEach
    void initData() {
        codeStub = new CodeStubDTO();
        codeStub.setFunctionName("solve");
        codeStub.setReturnType("int");
        codeStub.setReturnisArray(false);

        // Create parameter list
        List<CodeStubDTO.ParameterDTO> parameters = new ArrayList<>();

        // First parameter (arrayArg)
        CodeStubDTO.ParameterDTO param1 = new CodeStubDTO.ParameterDTO();
        param1.setId("13b0d2e9-f0d0-87f8-2554-1b6b949e8668");
        param1.setName("arrayArg");
        param1.setType("int");
        param1.setArray(true);
        parameters.add(param1);

        // Second parameter (argStr)
        CodeStubDTO.ParameterDTO param2 = new CodeStubDTO.ParameterDTO();
        param2.setId("7c90aef0-1cca-0c77-5e8b-0abb65bc3d90");
        param2.setName("argStr");
        param2.setType("String");
        param2.setArray(false);
        parameters.add(param2);

        // Assign parameters to codeStub
        codeStub.setParameters(parameters);
    }

    @Test
    void generateDriverCode() {
        System.out.println(codeGenerator.generateDriverCode(codeStub));
    }

    @Test
    void generateCodeStubString() {
        System.out.println(codeGenerator.generateCodeStubString(codeStub));
    }
}