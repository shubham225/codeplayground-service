package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.service.CodeSnippetService;
import com.shubham.codeplayground.service.execution.runner.CodeRunner;
import com.shubham.codeplayground.service.execution.runner.CodeRunnerFactory;

import java.util.List;

public class CodeSnippetServiceImpl implements CodeSnippetService {
    private final CodeRunnerFactory codeRunnerFactory;

    public CodeSnippetServiceImpl(CodeRunnerFactory codeRunnerFactory) {
        this.codeRunnerFactory = codeRunnerFactory;
    }

    @Override
    public CodeSnippet generateSnippet(Language language, CodeStubDTO codeStub, String Solution) {
        // TODO: Implementation - generate driver code and CodeStub and add solution if present
        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(language.toString());

        return null;
    }

    @Override
    public Boolean validate(CodeSnippet codeSnippet, List<Testcase> testcases) {
        //TODO: Implementation - compile run against the testcases to validate
        CodeRunner codeRunner = codeRunnerFactory.getCodeRunner(codeSnippet.getLanguage().toString());

        return false;
    }
}
