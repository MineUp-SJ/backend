package com.mineup.orchestrator.adapter.in.dto;

import com.mineup.orchestrator.enums.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDtoResponse {
    private  String id;
    private String name;
    private String categoryId;
    private ProductTypeEnum type;
    private Boolean isVerified;
}
