package com.mineup.orchestrator.adapter.out.persistence.mongo.document;

import com.mineup.orchestrator.enums.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDocument {
    @Id
    private String id;
    private String name;
    @Indexed
    private String categoryId;
    private String type;
    private List<String> photos;
    private Boolean isDeleted;
    private Boolean isVerified;
}
