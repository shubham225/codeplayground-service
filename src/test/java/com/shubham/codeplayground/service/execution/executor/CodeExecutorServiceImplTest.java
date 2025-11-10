package com.shubham.codeplayground.service.execution.executor;

import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.model.helper.LanguageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeExecutorServiceImplTest {

    CodeExecutorService codeExecutorService;
    LanguageProperties languageProperties;

    @BeforeEach
    void Init() {
        codeExecutorService = new CodeExecutorServiceImpl();
        languageProperties = getLanguageProperties();
    }

    public LanguageProperties getLanguageProperties() {
        return LanguageProperties.builder()
                .language(Language.JAVA)
                .interpretedLang(false)
                .compileCommand("javac")
                .execCommand("java")
                .dockerImage("openjdk:17")
                .build();
    }

    @Test
    void executeInDocker()
    {
        codeExecutorService.executeCode("Driver",
                                        "1 3 3 2 4 \"test\"",
                                        "/home/shubham-shinde/Temporary/CodingAppData/guest",
                                        languageProperties);
    }

}