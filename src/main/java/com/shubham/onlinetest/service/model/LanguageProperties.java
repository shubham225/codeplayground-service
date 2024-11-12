package com.shubham.onlinetest.service.model;

import com.shubham.onlinetest.model.enums.Language;
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
