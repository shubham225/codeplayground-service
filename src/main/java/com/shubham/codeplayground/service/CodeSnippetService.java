package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.enums.Language;

import java.util.List;

public interface CodeSnippetService {
    public CodeSnippet generateSnippet(Language language, CodeStubDTO codeStub, String Solution);
    public Boolean validate(CodeSnippet codeSnippet, List<Testcase> testcases);
}
