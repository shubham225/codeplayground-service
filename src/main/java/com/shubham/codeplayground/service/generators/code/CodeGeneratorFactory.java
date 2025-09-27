package com.shubham.codeplayground.service.generators.code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CodeGeneratorFactory {
    private final Map<String, CodeGenerator> codeGeneratorMap;

    public CodeGenerator getCodeGenerator(String key) {
        CodeGenerator codeGenerator = codeGeneratorMap.get(key);

        if (codeGenerator == null) {
            throw new RuntimeException("Code Generator not Found");
        }

        return codeGenerator;
    }
}
