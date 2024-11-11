package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.enums.Language;
import com.shubham.onlinetest.service.model.CodeExecutorResult;

public interface CodeExecutorService {
    public CodeExecutorResult executeCode(String sourceFile, String execDirPath, Language language);
}
