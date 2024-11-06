package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubmitReqDTO {
    private UUID problemId;
    private String code;
    private Language language;
}
