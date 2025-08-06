package com.shubham.codeplayground.model.dto;

import com.shubham.codeplayground.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {
    private Language language;
    private String code;
}
