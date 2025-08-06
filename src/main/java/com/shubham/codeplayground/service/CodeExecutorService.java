package com.shubham.codeplayground.service;

import com.shubham.codeplayground.service.model.CodeExecutorResult;
import com.shubham.codeplayground.service.model.LanguageProperties;

public interface CodeExecutorService {
    public CodeExecutorResult executeCode(String objectFile, String arguments, String execDirPath, LanguageProperties language);
    public CodeExecutorResult compileCode(String sourceFile, String arguments, String execDirPath, LanguageProperties language);
}
