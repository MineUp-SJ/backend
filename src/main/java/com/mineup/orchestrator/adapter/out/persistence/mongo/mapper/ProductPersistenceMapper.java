package com.mineup.orchestrator.adapter.out.persistence.mongo.mapper;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.ProductDocument;
import com.mineup.orchestrator.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

  /*  ProductDtoResponse toDto(Product product);
    Product toEntity(ProductDtoRequest productDtoRequest);
    @Mappings({
            @Mapping(target = "categoryId", source = "categoryId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false")
    })
    Product toEntity(ProductDtoRequest productDtoRequest,String categoryId);*/

    Product toDomain (ProductDocument productDocument);

    @Mappings({
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false"),
            @Mapping(target = "type", source = "product.type")
    })
    ProductDocument toDocument(Product product);

    @Mappings({
            @Mapping(target = "categoryId", source = "categoryId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false"),
            @Mapping(target = "type", source = "product.type")
    })
    ProductDocument toDocument(Product product, String categoryId);
}
