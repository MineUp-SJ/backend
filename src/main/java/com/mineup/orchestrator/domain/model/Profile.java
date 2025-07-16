package com.mineup.orchestrator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Profile {
    
    private String id;
    private String name;
    private String description;
    private Boolean isVerified;
    private Boolean isDeleted;
}
