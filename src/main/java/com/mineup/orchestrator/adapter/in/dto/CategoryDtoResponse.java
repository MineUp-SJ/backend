package com.mineup.orchestrator.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryDtoResponse {
    private  String id;
    private String name;
    private String parentId;
    private Boolean isVerified;
}
