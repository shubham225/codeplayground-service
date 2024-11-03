package com.shubham.onlinetest.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class IdentifierDTO {
    private UUID id;
}
