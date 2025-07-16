package com.mineup.orchestrator.domain.model;

import com.mineup.orchestrator.enums.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    private String id;
    private String name;
    private String categoryId;
    private ProductTypeEnum type;
    private List<String> photos;
    private Boolean isDeleted;
    private Boolean isVerified;
}
