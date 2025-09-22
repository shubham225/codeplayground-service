package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.service.CodeSnippetService;
import com.shubham.codeplayground.service.execution.runner.CodeRunner;
import com.shubham.codeplayground.service.execution.runner.CodeRunnerFactory;
import com.shubham.codeplayground.service.generators.code.CodeGenerator;
import com.shubham.codeplayground.service.generators.code.CodeGeneratorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CodeSnippetServiceImpl implements CodeSnippetService {
    private final CodeRunnerFactory codeRunnerFactory;
    private final CodeGeneratorFactory codeGeneratorFactory;

    @Override
    public CodeSnippet generateSnippet(Language language, CodeStubDTO codeStub, String solution) {
        // TODO: Implementation - generate driver code and CodeStub and add solution if present
        CodeGenerator codeGenerator = codeGeneratorFactory.getCodeGenerator(language.toString());

        String driverCode = codeGenerator.generateDriverCode(codeStub);
        String initCode = codeGenerator.generateCodeStubString(codeStub);

        return new CodeSnippet(language, initCode, driverCode, solution, null);
    }

    @Override
    public Boolean validate(CodeSnippet codeSnippet, List<Testcase> testcases) {
        //TODO: Implementation - compile run against the testcases to validate
        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(codeSnippet.getLanguage().toString());
        return false;
    }
}
