package com.shubham.codeplayground.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutorResult {
    private int execResult;
    private List<String> output;
    private long memoryUsage;
    private long execTime;

    public boolean isSuccess() {
        return (execResult == 0);
    }
}
