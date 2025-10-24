package com.shubham.codeplayground.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
public class CodeStubDTO {
    @NotBlank("Function name can't be blank")
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
