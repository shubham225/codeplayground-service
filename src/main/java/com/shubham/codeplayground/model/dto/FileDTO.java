package com.shubham.codeplayground.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FileDTO {
    private UUID id;
    private String filename;
}
