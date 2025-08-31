package com.shubham.codeplayground.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodeStubDTO {
    private String functionName;
    private String returnType;
    private boolean returnisArray;
    private List<ParameterDTO> parameters;

    @Data
    public static class ParameterDTO {
        private String id;
        private String name;
        private String type;
        private boolean isArray;
    }
}
