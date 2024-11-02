package com.shubham.onlinetest.model.entity;

import com.shubham.onlinetest.model.enums.Language;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippet extends BaseModel {
    private UUID problemID;
    private Language language;
    private String code;
}
