package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.service.model.CodeExecutorResult;
import com.shubham.onlinetest.service.model.LanguageProperties;

public interface CodeExecutorService {
    public CodeExecutorResult executeCode(String classFile, String execDirPath, LanguageProperties language);
    public CodeExecutorResult compileCode(String sourceFile, String execDirPath, LanguageProperties language);
}
