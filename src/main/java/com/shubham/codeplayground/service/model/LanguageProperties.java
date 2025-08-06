package com.shubham.codeplayground.service.model;

import com.shubham.codeplayground.model.enums.Language;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LanguageProperties {
    Language language;
    String dockerImage;
    boolean interpretedLang;
    String compileCommand;
    String execCommand;
}
