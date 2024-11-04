package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.ProblemDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Problem extends BaseModel {
    @Column(unique = true)
    private String urlCode;
    private String title;
    private ProblemDifficulty difficulty;
    @Column(columnDefinition = "TEXT")
    private String descriptionMd;
    private String testCasesPath;
    private Boolean isActive;
    @OneToMany
    private List<CodeSnippet> codeSnippets;
    private long maxExecutionTime;
}
