package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippet extends BaseModel {
    private Language language;
    private String code;
    @Column(columnDefinition = "TEXT")
    private String mainCode;
    @Column(columnDefinition = "TEXT")
    private String answerCode;
}
