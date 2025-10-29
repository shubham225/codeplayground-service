package com.shubham.codeplayground.model.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Getter
@Setter
public class FileDTO {
    private UUID id;
    @NotBlank
    private String filename;
}
