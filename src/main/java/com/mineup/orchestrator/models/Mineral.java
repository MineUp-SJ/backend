package com.mineup.orchestrator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "minerals")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mineral {
    @Id
    private String id;
    private String name;
    private Boolean isDeleted;
}
