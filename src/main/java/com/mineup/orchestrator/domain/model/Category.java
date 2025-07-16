package com.mineup.orchestrator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {

    private String id;
    private String name;
    private String parentId;
    private Boolean isDeleted;
    private Boolean isVerified;
}
