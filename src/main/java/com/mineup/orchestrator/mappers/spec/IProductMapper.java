package com.mineup.orchestrator.mappers.spec;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.requests.ProductDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductDtoResponse;
import com.mineup.orchestrator.models.Category;
import com.mineup.orchestrator.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface IProductMapper {

    ProductDtoResponse toDto(Product product);
    Product toEntity(ProductDtoRequest productDtoRequest);
    @Mappings({
            @Mapping(target = "categoryId", source = "categoryId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false")
    })
    Product toEntity(ProductDtoRequest productDtoRequest,String categoryId);
}
