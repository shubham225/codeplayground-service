package com.shubham.codeplayground.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseModel {
    @Column(unique = true)
    private String username;
    private int totalProblemSolved;
}
