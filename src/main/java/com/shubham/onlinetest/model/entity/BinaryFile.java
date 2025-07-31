package com.shubham.onlinetest.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BinaryFile extends BaseModel{
    private String name;
    private String fileType;
    @Lob
    private byte[] data;
}
