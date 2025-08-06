package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExecuteReqDTO {
    private UUID userProblemId;
    private Language language;
}
