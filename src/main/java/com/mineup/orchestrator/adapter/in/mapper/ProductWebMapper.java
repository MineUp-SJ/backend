package com.mineup.orchestrator.adapter.in.mapper;

import com.mineup.orchestrator.domain.model.Product;
import com.mineup.orchestrator.adapter.in.dto.ProductDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.ProductDtoResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ProductWebMapper {

    ProductDtoResponse toDto(Product product);
    Product toModel(ProductDtoRequest productDtoRequest);
    @Mappings({
            @Mapping(target = "categoryId", source = "categoryId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false")
    })
    Product toModel(ProductDtoRequest productDtoRequest,String categoryId);
}
