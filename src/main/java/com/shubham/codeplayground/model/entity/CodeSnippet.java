package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.enums.Language;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippet extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    @Enumerated(EnumType.ORDINAL)
    private Language language;
    private String code;
    @Column(columnDefinition = "TEXT")
    private String driverCode;
    @Column(columnDefinition = "TEXT")
    private String answerCode;
}
