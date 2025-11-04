package com.shubham.codeplayground.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CodeStubDTO {
    @NotBlank
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
