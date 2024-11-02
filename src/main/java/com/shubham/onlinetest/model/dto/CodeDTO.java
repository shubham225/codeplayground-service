package com.shubham.onlinetest.model.dto;

import com.shubham.onlinetest.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {
    private Language language;
    private String code;
}
