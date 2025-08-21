package com.shubham.codeplayground.model.entity.problem;

import com.shubham.codeplayground.model.entity.BaseModel;
import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
public class Problem extends BaseModel {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String descriptionMd;
    @Enumerated(EnumType.ORDINAL)
    private ProblemDifficulty difficulty;
    private Boolean isActive;
}
