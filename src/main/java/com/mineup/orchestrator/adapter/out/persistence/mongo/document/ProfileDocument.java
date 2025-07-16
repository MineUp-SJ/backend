package com.mineup.orchestrator.adapter.out.persistence.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private Boolean isVerified;
    private Boolean isDeleted;
}
