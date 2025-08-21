package com.shubham.codeplayground.model.helper;

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
    Boolean interpretedLang;
    String compileCommand;
    String execCommand;
}
