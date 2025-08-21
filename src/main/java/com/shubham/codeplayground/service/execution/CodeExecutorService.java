package com.shubham.codeplayground.service.execution;

import com.shubham.codeplayground.model.result.CodeExecutorResult;
import com.shubham.codeplayground.model.helper.LanguageProperties;

public interface CodeExecutorService {
    public CodeExecutorResult executeCode(String objectFile, String arguments, String execDirPath, LanguageProperties language);
    public CodeExecutorResult compileCode(String sourceFile, String arguments, String execDirPath, LanguageProperties language);
}
