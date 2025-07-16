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
public class ProductDtoRequest {
    private String name;
    private ProductTypeEnum type;
}
