package com.mineup.orchestrator.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductByCategoryDtoResponse {
        private String id;
        private String name;
        private List<ProductByCategoryDtoResponse> categories = new ArrayList<>();
        private List<ProductDtoResponse> products = new ArrayList<>();
}
