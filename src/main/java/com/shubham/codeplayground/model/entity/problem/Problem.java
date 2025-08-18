package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
public class Problem extends BaseModel{
    @Column(unique = true)
    private String urlCode;
    private String title;
    @Enumerated(EnumType.ORDINAL)
    private ProblemDifficulty difficulty;
    @Column(columnDefinition = "TEXT")
    private String descriptionMd;
    private Boolean isActive;
}
