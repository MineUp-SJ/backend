package com.mineup.orchestrator.adapter.out.persistence.mongo.mapper;


import com.mineup.orchestrator.adapter.out.persistence.mongo.document.CategoryDocument;
import com.mineup.orchestrator.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {

    /*  CategoryDtoResponse toDto(Category category);
      Category toEntity(CategoryDtoRequest categoryDtoRequest);
      @Mappings({
              @Mapping(target = "parentId", source = "parentId"),
              @Mapping(target = "isVerified",  constant = "false"),
              @Mapping(target = "isDeleted",  constant = "false")
      })
      Category toEntity(CategoryDtoRequest categoryDtoRequest,String parentId);*/
    @Mappings({
            @Mapping(target = "parentId", source = "parentId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false")
    })
    CategoryDocument toDocument(Category category, String parentId);
    CategoryDocument toDocument(Category category);
    Category toDomain(CategoryDocument categoryDocument);
}
