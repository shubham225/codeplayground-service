package com.shubham.onlinetest.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutorResult {
    private int execResult;
    private String output;

    public boolean isSuccess() {
        return (execResult == 0);
    }
}
