package com.shubham.codeplayground.model.entity;

import com.shubham.codeplayground.model.enums.ProblemDifficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Problem extends BaseModel {
    @Column(unique = true)
    private String urlCode;
    private String title;
    @Enumerated(EnumType.ORDINAL)
    private ProblemDifficulty difficulty;
    @Column(columnDefinition = "TEXT")
    private String descriptionMd;
    private String testCasesPath;
    private String answerKeyPath;
    private Boolean isActive;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CodeSnippet> codeSnippets = new ArrayList<>();
    private long maxExecutionTime;
}
