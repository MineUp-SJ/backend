package com.mineup.orchestrator.adapter.out.persistence.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDocument {
    @Id
    private String id;
    private String name;
    @Indexed
    private String parentId;
    private Boolean isDeleted;
    private Boolean isVerified;
}
